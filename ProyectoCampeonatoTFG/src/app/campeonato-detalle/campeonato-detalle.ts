import { Component, OnInit, signal, computed, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CampeonatoService } from '../service/campeonato-service';
import { CategoriaService } from '../service/categoria-service';
import { InscripcionService } from '../service/inscripcion-service';
import { Campeonato } from '../interfaces/campeonato';
import { Categoria } from '../interfaces/categoria';
import { Inscripcion } from '../interfaces/inscripcion';

export interface CombateFrontend {
  numeroCombate: number;
  ronda: string;
  estado: string;
  puntuacionRojo: number;
  puntuacionAzul: number;
  senshu: string | null;
  nombreRojo: string;
  nombreAzul: string | null;
  idRojo: number;
  idAzul: number | null;
}

export interface RondaBracket {
  etiqueta: string;   // "Ronda 1", "Ronda 2", "Repesca", "Final"
  tipo: 'ronda' | 'repesca' | 'final';
  combates: CombateFrontend[];
}

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

  // Campeonato
  campeonato = signal<Campeonato | null>(null);

  // Categorias del campeonato
  categorias = signal<Categoria[]>([]);

  categoriaSeleccionada = signal<Categoria | null>(null);

  inscripciones = signal<Inscripcion[]>([]);

  combates = signal<CombateFrontend[]>([]);

  modalAbierto = signal(false);

  readonly objectKeys = Object.keys;

  // Agrupar por género y modalidad
  masculino = computed(() => this.agruparPorModalidad(this.categorias().filter(c => c.genero === 'M')));
  femenino = computed(() =>  this.agruparPorModalidad(this.categorias().filter(c => c.genero === 'F')));

  // Bracket organizado por rondas
  bracket = computed<RondaBracket[]>(() => {
    const todos = this.combates();
    if (todos.length === 0) return [];

    const mapa = new Map<string, CombateFrontend[]>();
    for (const c of todos) {
      const key = c.ronda;
      if (!mapa.has(key)) mapa.set(key, []);
      mapa.get(key)!.push(c);
    }

    const result: RondaBracket[] = [];

    // Orden conocido de rondas normales
    const ordenRondas = ['R1', 'R2', 'R3', 'R4', 'R5', 'SF', 'QF'];
    for (const key of ordenRondas) {
      if (mapa.has(key)) {
        const num = key === 'SF' ? 'Semifinal' : key === 'QF' ? 'Cuartos de Final' : `Ronda ${key.replace('R', '')}`;
        result.push({ etiqueta: num, tipo: 'ronda', combates: mapa.get(key)! });
        mapa.delete(key);
      }
    }

    // Repesca
    if (mapa.has('REP') || mapa.has('REPESCA')) {
      const key = mapa.has('REP') ? 'REP' : 'REPESCA';
      result.push({ etiqueta: 'Repesca', tipo: 'repesca', combates: mapa.get(key)! });
      mapa.delete(key);
    }

    // Final
    if (mapa.has('FINAL') || mapa.has('F')) {
      const key = mapa.has('FINAL') ? 'FINAL' : 'F';
      result.push({ etiqueta: 'Final', tipo: 'final', combates: mapa.get(key)! });
      mapa.delete(key);
    }

    // Cualquier otra ronda no reconocida
    for (const [key, combatesList] of mapa.entries()) {
      result.push({ etiqueta: key, tipo: 'ronda', combates: combatesList });
    }

    return result;
  });

  // ¿Está terminado el campeonato?
  campeonatoFinalizado = computed(() => this.campeonato()?.estado === 'pasado');

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
    this.combates.set([]);

    try {
      const data = await this.InscServ.getInscritosPorCategoria(camp.id_campeonato, cat.id_categoria);
      this.inscripciones.set(data);
    } catch {
      this.inscripciones.set([]);
    }

    // Cargar combates si hay inscritos
    try {
      const resp = await fetch(`http://localhost:8080/api/combates/campeonato/${camp.id_campeonato}/categoria/${cat.id_categoria}`);
      if (resp.ok) {
        const raw: any[] = await resp.json();
        const mapped: CombateFrontend[] = raw.map(c => ({
          numeroCombate: c.id?.numeroCombate ?? c.numeroCombate,
          ronda: c.ronda,
          estado: c.estado,
          puntuacionRojo: c.puntuacionRojo ?? 0,
          puntuacionAzul: c.puntuacionAzul ?? 0,
          senshu: c.senshu ?? null,
          nombreRojo: c.competidorRojo
            ? `${c.competidorRojo.nombre} ${c.competidorRojo.apellidos}`
            : 'Desconocido',
          nombreAzul: c.competidorAzul
            ? `${c.competidorAzul.nombre} ${c.competidorAzul.apellidos}`
            : null,
          idRojo: c.competidorRojo?.idUsuario ?? 0,
          idAzul: c.competidorAzul?.idUsuario ?? null,
        }));
        this.combates.set(mapped);
      }
    } catch {
      this.combates.set([]);
    }
  }

  cerrarModal() {
    this.modalAbierto.set(false);
    this.categoriaSeleccionada.set(null);
    this.inscripciones.set([]);
    this.combates.set([]);
  }

  /** Devuelve 'rojo' | 'azul' | null según quién ganó el combate */
  ganadorCombate(c: CombateFrontend): 'rojo' | 'azul' | null {
    if (c.estado !== 'finalizado') return null;
    if (c.nombreAzul === null) return 'rojo'; // bye automático
    if (c.puntuacionRojo > c.puntuacionAzul) return 'rojo';
    if (c.puntuacionAzul > c.puntuacionRojo) return 'azul';
    // Empate: desempate por senshu
    if (c.senshu === 'rojo') return 'azul'; // senshu = aviso, pierde quien lo tiene
    if (c.senshu === 'azul') return 'rojo';
    return null;
  }

  private agruparPorModalidad(cats: Categoria[]): Record<string, Categoria[]> {
    return cats.reduce((acc, cat) => {
      const key = cat.modalidad.charAt(0).toUpperCase() + cat.modalidad.slice(1);
      (acc[key] ??= []).push(cat);
      return acc;
    }, {} as Record<string, Categoria[]>);
  }
}
