import { Component, OnInit, signal, computed } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CampeonatoService } from '../service/campeonato-service';
import { CategoriaService } from '../service/categoria-service';
import { Campeonato } from '../interfaces/campeonato';
import { Categoria } from '../interfaces/categoria';

export interface InscripcionDTO {
  idCompetidor: number;
  nombreCompetidor: string;
  clubCompetidor: string;
}

@Component({
  selector: 'app-campeonato-detalle',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './campeonato-detalle.html',
  styleUrl: './campeonato-detalle.css',
})
export class CampeonatoDetalleComponent implements OnInit {
  campeonato        = signal<Campeonato | null>(null);
  categorias        = signal<Categoria[]>([]);
  loadingCategorias = signal(true);

  // Modal de inscripciones
  categoriaSeleccionada = signal<Categoria | null>(null);
  inscripciones         = signal<InscripcionDTO[]>([]);
  loadingInscrip        = signal(false);
  modalAbierto          = signal(false);

  readonly objectKeys = Object.keys;

  masculino = computed(() =>
    this.agruparPorModalidad(this.categorias().filter(c => c.genero === 'M'))
  );
  femenino = computed(() =>
    this.agruparPorModalidad(this.categorias().filter(c => c.genero === 'F'))
  );

  constructor(
    private route: ActivatedRoute,
    private svc: CampeonatoService,
    private catSvc: CategoriaService
  ) {}

  async ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
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
    this.categoriaSeleccionada.set(cat);
    this.modalAbierto.set(true);
    this.loadingInscrip.set(true);
    this.inscripciones.set([]);

    try {
      const idCamp = this.campeonato()!.id_campeonato;
      const res = await fetch(
        `http://localhost:8080/api/inscripciones/campeonato/${idCamp}/categoria/${cat.id}`
      );
      if (!res.ok) throw new Error();
      this.inscripciones.set(await res.json());
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
