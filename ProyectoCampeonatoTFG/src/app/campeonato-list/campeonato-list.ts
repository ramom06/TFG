import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule }       from '@angular/common';
import { FormsModule }        from '@angular/forms';
import { CampeonatoService }  from '../campeonato-service';
import { Campeonato, Estado, Nivel } from '../campeonato';

@Component({
  selector: 'app-campeonato-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './campeonato-list.html',
  styleUrl: './campeonato-list.css',
})
export class CampeonatoListComponent implements OnInit {

  // ── Estado ──────────────────────────────────────────────
  campeonatos = signal<Campeonato[]>([]);
  loading     = signal(true);
  error       = signal<string | null>(null);

  // ── Filtros ─────────────────────────────────────────────
  searchText  = signal('');
  filtroEstado = signal<Estado | 'todos'>('todos');
  filtroNivel  = signal<Nivel  | 'todos'>('todos');
  sortField    = signal<'fechaInicio' | 'nombre'>('fechaInicio');
  sortDir      = signal<'asc' | 'desc'>('asc');

  // ── Lista filtrada y ordenada (derivada) ─────────────────
  filtered = computed(() => {
    let lista = this.campeonatos();

    const txt = this.searchText().toLowerCase();
    if (txt)
      lista = lista.filter(c =>
        c.nombre.toLowerCase().includes(txt) ||
        c.ubicacion.toLowerCase().includes(txt)
      );

    if (this.filtroEstado() !== 'todos')
      lista = lista.filter(c => c.estado === this.filtroEstado());

    if (this.filtroNivel() !== 'todos')
      lista = lista.filter(c => c.nivel === this.filtroNivel());

    return [...lista].sort((a, b) => {
      const f = this.sortField();
      const cmp = f === 'nombre'
        ? a.nombre.localeCompare(b.nombre)
        : new Date(a.fechaInicio).getTime() - new Date(b.fechaInicio).getTime();
      return this.sortDir() === 'asc' ? cmp : -cmp;
    });
  });

  constructor(private svc: CampeonatoService) {}

  async ngOnInit() {
    try {
      const data = await this.svc.getAllCampeonatos();
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

  resetFiltros() {
    this.searchText.set('');
    this.filtroEstado.set('todos');
    this.filtroNivel.set('todos');
    this.sortField.set('fechaInicio');
    this.sortDir.set('asc');
  }

  badgeClass(estado: Estado): string {
    return {
      futuro: 'badge-futuro',
      activo: 'badge-activo',
      pasado: 'badge-pasado',
    }[estado];
  }

  estadoLabel(estado: Estado): string {
    return { futuro: 'Próximo', activo: 'En curso', pasado: 'Finalizado' }[estado];
  }

  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('es-ES', {
      day: '2-digit', month: 'short', year: 'numeric'
    });
  }
}
