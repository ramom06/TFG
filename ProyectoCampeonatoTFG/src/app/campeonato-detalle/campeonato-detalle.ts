import { Component, OnInit, signal, computed, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CampeonatoService } from '../service/campeonato-service';
import { CategoriaService } from '../service/categoria-service';
import { InscripcionService } from '../service/inscripcion-service';
import { CombateService } from '../service/combate-service';
import { Campeonato } from '../interfaces/campeonato';
import { Categoria } from '../interfaces/categoria';
import { Inscripcion } from '../interfaces/inscripcion';
import { Combate } from '../interfaces/combate';
import {Ronda} from '../interfaces/ronda';

@Component({
  selector: 'app-campeonato-detalle',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './campeonato-detalle.html',
  styleUrl: './campeonato-detalle.css',
})
export class CampeonatoDetalleComponent implements OnInit {

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

  readonly objectKeys = Object.keys;

  masculino = computed(() => this.agruparPorModalidad(this.categorias().filter(c => c.genero === 'M')));
  femenino  = computed(() => this.agruparPorModalidad(this.categorias().filter(c => c.genero === 'F')));

  campeonatoFinalizado = computed(() => this.campeonato()?.estado === 'pasado');

  /** Organiza los combates en secciones: rondas normales → repesca → final */
  bracket = computed<Ronda[]>(() => {
    const todos = this.combates();
    if (todos.length === 0) return [];

    const mapa = new Map<string, Combate[]>();
    for (const c of todos) {
      if (!mapa.has(c.ronda)) mapa.set(c.ronda, []);
      mapa.get(c.ronda)!.push(c);
    }

    const result: Ronda[] = [];

    // Rondas numéricas y especiales ordenadas
    for (const key of ['R1', 'R2', 'R3', 'R4', 'R5', 'QF', 'SF']) {
      if (!mapa.has(key)) continue;
      const etiq = key === 'SF' ? 'Semifinal' : key === 'QF' ? 'Cuartos de Final' : `Ronda ${key.replace('R', '')}`;
      result.push({ etiqueta: etiq, tipo: 'ronda', combates: mapa.get(key)! });
      mapa.delete(key);
    }

    // Repesca
    for (const key of ['REP', 'REPESCA']) {
      if (!mapa.has(key)) continue;
      result.push({ etiqueta: 'Repesca', tipo: 'repesca', combates: mapa.get(key)! });
      mapa.delete(key);
    }

    // Final
    for (const key of ['F', 'FINAL']) {
      if (!mapa.has(key)) continue;
      result.push({ etiqueta: 'Final', tipo: 'final', combates: mapa.get(key)! });
      mapa.delete(key);
    }

    // Cualquier ronda desconocida
    for (const [key, list] of mapa.entries()) {
      result.push({ etiqueta: key, tipo: 'ronda', combates: list });
    }

    return result;
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
    this.inscripciones.set([]);
    this.combates.set([]);
    this.modalAbierto.set(true);

    try {
      this.inscripciones.set(
        await this.InscServ.getInscritosPorCategoria(camp.id_campeonato, cat.id_categoria)
      );
    } catch {
      this.inscripciones.set([]);
    }

    if (this.inscripciones().length > 0) {
      try {
        this.combates.set(
          await this.CombServ.getCombatesPorCategoriaYCampeonato(camp.id_campeonato, cat.id_categoria)
        );
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

  /** Devuelve 'rojo' | 'azul' | null según el ganador del combate */
  ganadorCombate(c: Combate): 'rojo' | 'azul' | null {
    if (c.estado !== 'finalizado') return null;
    if (!c.competidorAzul) return 'rojo'; // bye
    if (c.puntuacionRojo > c.puntuacionAzul) return 'rojo';
    if (c.puntuacionAzul > c.puntuacionRojo) return 'azul';
    if (c.senshu === 'rojo') return 'azul'; // senshu = aviso, pierde quien lo tiene
    if (c.senshu === 'azul') return 'rojo';
    return null;
  }

  nombreCompleto(c: { nombre: string; apellidos: string }): string {
    return `${c.nombre} ${c.apellidos}`;
  }

  private agruparPorModalidad(cats: Categoria[]): Record<string, Categoria[]> {
    return cats.reduce((acc, cat) => {
      const key = cat.modalidad.charAt(0).toUpperCase() + cat.modalidad.slice(1);
      (acc[key] ??= []).push(cat);
      return acc;
    }, {} as Record<string, Categoria[]>);
  }
}
