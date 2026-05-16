import { Component, OnInit, signal, computed, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CampeonatoService } from '../service/campeonato-service';
import { CategoriaService } from '../service/categoria-service';
import { InscripcionService } from '../service/inscripcion-service';
import { CombateService } from '../service/combate-service';
import { InscripcionComponent } from '../inscripcion/inscripcion';
import { Campeonato } from '../interfaces/campeonato';
import { Categoria } from '../interfaces/categoria';
import { Inscripcion } from '../interfaces/inscripcion';
import { Combate } from '../interfaces/combate';

@Component({
  selector: 'app-campeonato-detalle',
  standalone: true,
  imports: [CommonModule, RouterLink, InscripcionComponent],
  templateUrl: './campeonato-detalle.html'
})
export class CampeonatoDetalle implements OnInit {

  private route    = inject(ActivatedRoute);
  private CampServ = inject(CampeonatoService);
  private CatServ  = inject(CategoriaService);
  private InscServ = inject(InscripcionService);
  private CombServ = inject(CombateService);

  campeonato            = signal<Campeonato | null>(null);
  categorias            = signal<Categoria[]>([]);
  categoriaSeleccionada = signal<Categoria | null>(null);
  inscripciones         = signal<Inscripcion[]>([]);
  combates              = signal<Combate[]>([]);
  modalAbierto          = signal(false);

  // Controla si el modal de inscripción está abierto
  inscripcionAbierta    = signal(false);

  masculino = computed(() => this.agruparPorModalidad(this.categorias().filter(c => c.genero === 'M')));
  femenino  = computed(() => this.agruparPorModalidad(this.categorias().filter(c => c.genero === 'F')));
  modalidadesMasculino = computed(() => Object.keys(this.masculino()));
  modalidadesFemenino  = computed(() => Object.keys(this.femenino()));
  campeonatoFinalizado = computed(() => this.campeonato()?.estado === 'pasado');

  async ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    const campeonatos = await this.CampServ.getAllCampeonatos();
    this.campeonato.set(campeonatos.find(c => c.idCampeonato === id) || null);
    try {
      this.categorias.set(await this.CatServ.getCategoriasPorCampeonato(id));
    } catch {
      this.categorias.set([]);
    }
  }

  //Ventana de participantes
  async abrirModalParticipantes(cat: Categoria) {
    const camp = this.campeonato();
    if (!camp) return;

    this.categoriaSeleccionada.set(cat);
    this.modalAbierto.set(true);

    try {
      this.inscripciones.set(await this.InscServ.getInscritosPorCategoria(camp.idCampeonato, cat.idCategoria));
    } catch {
      this.inscripciones.set([]);
    }

    if (this.inscripciones().length > 0) {
      try {
        this.combates.set(await this.CombServ.getCombatesPorCategoriaYCampeonato(camp.idCampeonato, cat.idCategoria));
      } catch {
        this.combates.set([]);
      }
    }
  }

  cerrarModal() {
    this.modalAbierto.set(false);
    this.categoriaSeleccionada.set(null);
    this.inscripciones.set([]);
    this.combates.set([]);
  }

  abrirInscripcion()  { this.inscripcionAbierta.set(true); }
  cerrarInscripcion() { this.inscripcionAbierta.set(false); }

  onInscritoOk() {
    this.inscripcionAbierta.set(false);
    // Recarga inscripciones si hay modal de categoría abierto
    const cat = this.categoriaSeleccionada();
    if (cat) this.abrirModalParticipantes(cat);
  }

  private agruparPorModalidad(cats: Categoria[]): Map<string, Categoria[]> {
    return cats.reduce((acc, cat) => {
      const key = cat.modalidad.charAt(0).toUpperCase() + cat.modalidad.slice(1);
      if (!acc.has(key)) acc.set(key, []);
      acc.get(key)!.push(cat);
      return acc;
    }, new Map<string, Categoria[]>());
  }
}
