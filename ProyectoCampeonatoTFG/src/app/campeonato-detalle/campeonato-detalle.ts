import { Component, OnInit, signal, computed, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CampeonatoService } from '../service/campeonato-service';
import { CategoriaService } from '../service/categoria-service';
import { InscripcionService } from '../service/inscripcion-service';
import { Campeonato } from '../interfaces/campeonato';
import { Categoria } from '../interfaces/categoria';
import { Inscripcion } from '../interfaces/inscripcion';

@Component({
  selector: 'app-campeonato-detalle',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './campeonato-detalle.html',
  styleUrl: './campeonato-detalle.css',
})
export class CampeonatoDetalleComponent implements OnInit {
  // Inyecciones
  private route = inject(ActivatedRoute);
  private CampServ = inject(CampeonatoService);
  private CatServ = inject(CategoriaService);
  private InscServ = inject(InscripcionService);

  //Campeonato
  campeonato = signal<Campeonato | null>(null);

  //Categorias del campeonato
  categorias = signal<Categoria[]>([]);

  categoriaSeleccionada = signal<Categoria | null>(null);

  inscripciones = signal<Inscripcion[]>([]);

  modalAbierto = signal(false);

  readonly objectKeys = Object.keys;

  // Agrupar por género y modalidad
  masculino = computed(() => this.agruparPorModalidad(this.categorias().filter(c => c.genero === 'M')));
  femenino = computed(() =>  this.agruparPorModalidad(this.categorias().filter(c => c.genero === 'F')));

  async ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    const campeonatos = await this.CampServ.getAllCampeonatos();
    this.campeonato.set(campeonatos.find(c => c.id_campeonato === id) || null);

    try {
      this.categorias.set(await this.CatServ.getCategoriasPorCampeonato(id));
    } catch {
      this.categorias.set([]);
    }
  }

  async abrirModalCategoria(cat: Categoria) {
    const camp = this.campeonato();
    if (!camp) return;

    this.categoriaSeleccionada.set(cat);
    this.modalAbierto.set(true);

    try {
      // Llamada al nuevo servicio para obtener inscritos
      const data = await this.InscServ.getInscritosPorCategoria(camp.id_campeonato, cat.id);
      this.inscripciones.set(data);
    } catch {
      this.inscripciones.set([]);
    }
  }

  cerrarModal() {
    this.modalAbierto.set(false);
    this.categoriaSeleccionada.set(null);
    this.inscripciones.set([]);
  }

  private agruparPorModalidad(cats: Categoria[]): Record<string, Categoria[]> {
    return cats.reduce((acc, cat) => {
      const key = cat.modalidad.charAt(0).toUpperCase() + cat.modalidad.slice(1);
      (acc[key] ??= []).push(cat);
      return acc;
    }, {} as Record<string, Categoria[]>);
  }
}
