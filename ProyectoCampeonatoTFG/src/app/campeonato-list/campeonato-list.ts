import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule }       from '@angular/common';
import { FormsModule }        from '@angular/forms';
import { CampeonatoService }  from '../service/campeonato-service';
import { Campeonato, Estado, EstadoVisual, Nivel } from '../interfaces/campeonato';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-campeonato-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './campeonato-list.html',
})
export class CampeonatoListComponent implements OnInit {


  campeonatos  = signal<Campeonato[]>([]);
  loading      = signal(true);
  error        = signal<string | null>(null);
  searchText   = signal('');

  filtroEstado = signal<Estado | 'todos'>('todos');

  filtroNivel  = signal<Nivel  | 'todos'>('todos');

  sortField    = signal<'fechaInicio' | 'nombre'>('fechaInicio');

  sortDir      = signal<'asc' | 'desc'>('asc');


  filtered = computed(() => {
    let lista = this.campeonatos();

    let txt = this.normalize(this.searchText());

    if (txt)
      lista = lista.filter(c =>
        this.normalize(c.nombre).includes(txt) || this.normalize(c.ubicacion).includes(txt)
      );

    if (this.filtroEstado() !== 'todos')
      lista = lista.filter(c => this.estadoCalculado(c) === this.filtroEstado());

    if (this.filtroNivel() !== 'todos')
      lista = lista.filter(c => c.nivel.toLowerCase() === this.filtroNivel());

    return [...lista].sort((a, b) => {
      if(this.sortField() === 'nombre'){
        return this.sortDir() === 'asc' ? a.nombre.localeCompare(b.nombre) : b.nombre.localeCompare(a.nombre);
      }else {
        return this.sortDir() === 'asc' ? new Date(a.fechaInicio).getTime() - new Date(b.fechaInicio).getTime() : new Date(b.fechaInicio).getTime() - new Date(a.fechaInicio).getTime();
      }
    });
  });

  // El estado del backend ("futuro", "inscripciones_cerradas", "pasado") puede no
  // coincidir con la realidad temporal (un campeonato "futuro" con fechaFin pasada
  // sigue marcado mal). Para la UI calculamos el estado por fechas.
  estadoCalculado(c: Campeonato): EstadoVisual {
    const hoy = new Date(); hoy.setHours(0, 0, 0, 0);
    const inicio = new Date(c.fechaInicio); inicio.setHours(0, 0, 0, 0);
    const fin    = new Date(c.fechaFin);    fin.setHours(0, 0, 0, 0);
    if (fin < hoy)    return 'pasado';
    if (inicio > hoy) return 'futuro';
    return 'activo';
  }


  async ngOnInit() {
    const svc = new CampeonatoService();

    try {
      const data = await svc.getAllCampeonatos();
      this.campeonatos.set(data);
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al cargar campeonatos');
    } finally {
      this.loading.set(false);
    }
  }

  // ── Helpers para el template ─────────────────────────────
  toggleDir() {
    this.sortDir.update(d => d === 'asc' ? 'desc' : 'asc');
  }

  badgeClass(estado: EstadoVisual): string {
    return {
      futuro: 'badge-futuro',
      activo: 'badge-activo',
      pasado: 'badge-pasado',
    }[estado];
  }

  estadoLabel(estado: EstadoVisual): string {
    return { futuro: 'Próximo', activo: 'En curso', pasado: 'Finalizado' }[estado];
  }

  //Esta función filtra las tildes y las mayúsculas
  private normalize(text: string): string {
    return text.toLowerCase()

      //Separa internamente el carácter acentuado en 2
      .normalize('NFD')

      // En todos los carácteres unicode con acentos y en todas las cadenas reemplaza con ''

      .replace(/[\u0300-\u036f]/g, '');
  }
}
