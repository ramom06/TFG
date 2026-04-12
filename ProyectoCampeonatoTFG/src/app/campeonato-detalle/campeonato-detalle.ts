import { Component, OnInit, signal, computed, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CampeonatoService }    from '../service/campeonato-service';
import { CategoriaService }     from '../service/categoria-service';
import { InscripcionService }   from '../service/inscripcion-service';
import { Campeonato }   from '../interfaces/campeonato';
import { Categoria }    from '../interfaces/categoria';
import { Inscripcion }  from '../interfaces/inscripcion';
import { InscripcionComponent } from '../inscripcion/inscripcion';

@Component({
  selector: 'app-campeonato-detalle',
  standalone: true,
  imports: [CommonModule, RouterLink, InscripcionComponent],
  templateUrl: './campeonato-detalle.html',
})
export class CampeonatoDetalle implements OnInit {
  private route    = inject(ActivatedRoute);
  private CampServ = inject(CampeonatoService);
  private CatServ  = inject(CategoriaService);
  private InscServ = inject(InscripcionService);

  campeonato            = signal<Campeonato | null>(null);
  categorias            = signal<Categoria[]>([]);
  categoriaSeleccionada = signal<Categoria | null>(null);
  inscripciones         = signal<Inscripcion[]>([]);
  modalAbierto          = signal(false);

  // Modal de inscripción
  inscripcionModalAbierto = signal(false);
  inscripcionExitosa      = signal(false);

  readonly objectKeys = Object.keys;

  masculino = computed(() => this.agruparPorModalidad(this.categorias().filter(c => c.genero === 'M')));
  femenino  = computed(() => this.agruparPorModalidad(this.categorias().filter(c => c.genero === 'F')));

  // Solo mostramos el botón de inscripción en campeonatos futuros o activos
  puedeInscribirse = computed(() => {
    const c = this.campeonato();
    return c ? c.estado === 'futuro' || c.estado === 'activo' : false;
  });

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
      const data = await this.InscServ.getInscritosPorCategoria(camp.id_campeonato, cat.id_categoria);
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

  // ── Inscripción ────────────────────────────────────────────────────────────
  abrirInscripcionModal()  { this.inscripcionModalAbierto.set(true);  this.inscripcionExitosa.set(false); }
  cerrarInscripcionModal() { this.inscripcionModalAbierto.set(false); }

  onInscritoOk() {
    this.inscripcionModalAbierto.set(false);
    this.inscripcionExitosa.set(true);
    // Ocultar toast tras 4 segundos
    setTimeout(() => this.inscripcionExitosa.set(false), 4000);
  }

  private agruparPorModalidad(cats: Categoria[]): Record<string, Categoria[]> {
    return cats.reduce((acc, cat) => {
      const key = cat.modalidad.charAt(0).toUpperCase() + cat.modalidad.slice(1);
      (acc[key] ??= []).push(cat);
      return acc;
    }, {} as Record<string, Categoria[]>);
  }
}
