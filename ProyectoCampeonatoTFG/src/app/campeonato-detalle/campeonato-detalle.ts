import { Component, OnInit, signal, computed, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CampeonatoService } from '../service/campeonato-service';
import { CategoriaService } from '../service/categoria-service';
import { InscripcionService } from '../service/inscripcion.service';
import { Campeonato } from '../interfaces/campeonato';
import { Categoria } from '../interfaces/categoria';
import { InscripcionDTO } from '../interfaces/inscripcion';

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
  private svc = inject(CampeonatoService);
  private catSvc = inject(CategoriaService);
  private insSvc = inject(InscripcionService);

  // Signals de estado
  campeonato = signal<Campeonato | null>(null);
  categorias = signal<Categoria[]>([]);
  loadingCategorias = signal(true);

  // Modal de inscripciones
  categoriaSeleccionada = signal<Categoria | null>(null);
  inscripciones = signal<InscripcionDTO[]>([]);
  loadingInscrip = signal(false);
  modalAbierto = signal(false);

  readonly objectKeys = Object.keys;

  // Agrupación automática por género y modalidad
  masculino = computed(() =>
    this.agruparPorModalidad(this.categorias().filter(c => c.genero === 'M'))
  );
  femenino = computed(() =>
    this.agruparPorModalidad(this.categorias().filter(c => c.genero === 'F'))
  );

  async ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    // Carga de datos iniciales
    const todos = await this.svc.getAllCampeonatos();
    this.campeonato.set(todos.find(c => c.id_campeonato === id) || null);

    try {
      this.categorias.set(await this.catSvc.getCategoriasPorCampeonato(id));
    } catch {
      this.categorias.set([]);
    } finally {
      this.loadingCategorias.set(false);
    }
  }

  async abrirModalCategoria(cat: Categoria) {
    const camp = this.campeonato();
    if (!camp) return;

    this.categoriaSeleccionada.set(cat);
    this.modalAbierto.set(true);
    this.loadingInscrip.set(true);

    try {
      // Llamada al nuevo servicio para obtener inscritos
      const data = await this.insSvc.getInscritosPorCategoria(camp.id_campeonato, cat.id);
      this.inscripciones.set(data);
    } catch {
      this.inscripciones.set([]);
    } finally {
      this.loadingInscrip.set(false);
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
