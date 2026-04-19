import { Component, OnInit, signal, inject, computed } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Sorteo } from '../../interfaces/sorteo';
import { Combate } from '../../interfaces/combate';
import { Ronda } from '../../interfaces/ronda';
import { Competidor } from '../../interfaces/competidor';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-sorteo',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './sorteo.html',
  styleUrl: './sorteo.css',
})
export class SorteoComponent implements OnInit {
  private route = inject(ActivatedRoute);

  sorteoData  = signal<Sorteo | null>(null);
  loading     = signal(true);
  error       = signal<string | null>(null);

  idCampeonato = signal(0);
  idCategoria  = signal(0);

  ganador = computed(() => {
    const s = this.sorteoData();
    if (!s) return null;
    // Buscar la última ronda (Final o la que tenga un solo combate)
    const rondas = s.rondas;
    if (rondas.length === 0) return null;
    const ultimaRonda = rondas[rondas.length - 1];
    if (ultimaRonda.combates.length === 0) return null;
    const combFinal = ultimaRonda.combates[0];
    if (combFinal.estado !== 'finalizado') return null;
    if (combFinal.puntuacionRojo > combFinal.puntuacionAzul) return combFinal.competidorRojo;
    if (combFinal.puntuacionAzul > combFinal.puntuacionRojo) return combFinal.competidorAzul;
    return null;
  });

  async ngOnInit() {
    const idC   = Number(this.route.snapshot.paramMap.get('id'));
    const idCat = Number(this.route.snapshot.paramMap.get('idCategoria'));
    this.idCampeonato.set(idC);
    this.idCategoria.set(idCat);

    try {
      // 1. Obtener inscritos en esa categoría del campeonato
      const resIns = await fetch(
        `${environment.apiUrl}/api/inscripciones/campeonato/${idC}/categoria/${idCat}`
      );
      if (!resIns.ok) throw new Error('No se pudieron cargar los inscritos');
      const inscritos: any[] = await resIns.json();

      // 2. Obtener combates ya generados (si los hay)
      const resComb = await fetch(
        `${environment.apiUrl}/api/combates/campeonato/${idC}/categoria/${idCat}`
      );
      const combates: any[] = resComb.ok ? await resComb.json() : [];

      // 3. Construir el sorteo
      const sorteo = this.construirSorteo(idC, idCat, inscritos, combates);
      this.sorteoData.set(sorteo);

    } catch (e: any) {
      this.error.set(e.message ?? 'Error al cargar el sorteo');
    } finally {
      this.loading.set(false);
    }
  }

  // ── Construcción del sorteo ───────────────────────────────────────────────

  private construirSorteo(
    idCampeonato: number,
    idCategoria: number,
    inscritos: any[],
    combates: any[]
  ): Sorteo {

    const nombreCategoria  = inscritos[0]?.nombreCategoria  ?? '';
    const nombreCampeonato = inscritos[0]?.nombreCampeonato ?? '';

    // Si hay combates en BD, los usamos y generamos todas las rondas del bracket
    if (combates.length > 0) {
      const rondas = this.construirBracketDesdeCombates(combates);
      return { idCampeonato, idCategoria, nombreCategoria, nombreCampeonato, rondas };
    }

    // Sin combates en BD: generamos bracket aleatorio desde inscripciones
    if (inscritos.length === 0) {
      return { idCampeonato, idCategoria, nombreCategoria, nombreCampeonato, rondas: [] };
    }

    const competidores = [...inscritos].sort(() => Math.random() - 0.5);
    const rondas = this.generarBracketCompleto(competidores);

    return { idCampeonato, idCategoria, nombreCategoria, nombreCampeonato, rondas };
  }

  /**
   * Toma los combates de BD (que pueden ser solo R1 o múltiples rondas)
   * y construye todas las rondas del bracket, generando las siguientes
   * a partir de los ganadores de cada ronda anterior.
   */
  private construirBracketDesdeCombates(combates: any[]): Ronda[] {
    const ordenClaves = ['R1', 'R2', 'R3', 'Semifinal', 'Final'];

    // Agrupar combates de BD por clave de ronda
    const porClave = new Map<string, any[]>();
    for (const c of combates) {
      const key = c.ronda ?? 'R1';
      if (!porClave.has(key)) porClave.set(key, []);
      porClave.get(key)!.push(c);
    }

    // Determinar cuál es la primera clave presente
    const clavesPresentes = ordenClaves.filter(k => porClave.has(k));
    if (clavesPresentes.length === 0) return [];

    const rondas: Ronda[] = [];

    // Convertir todas las rondas que ya existen en BD
    for (const clave of clavesPresentes) {
      rondas.push({
        etiqueta: this.labelRonda(clave),
        tipo:     clave === 'Final' || clave === 'F' ? 'final'
          : clave === 'REP' || clave === 'REPESCA' ? 'repesca'
            : 'ronda',
        combates: porClave.get(clave)!.map(c => this.mapCombate(c))
      });
    }

    // A partir de la última ronda con datos, generar las siguientes
    // usando los ganadores de cada ronda finalizada
    let rondasGeneradas = this.generarRondasFaltantes(rondas, ordenClaves, clavesPresentes);
    return [...rondas, ...rondasGeneradas];
  }

  /**
   * A partir de las rondas ya cargadas de BD, genera las rondas siguientes
   * usando los ganadores de cada ronda anterior.
   */
  private generarRondasFaltantes(
    rondasExistentes: Ronda[],
    ordenClaves: string[],
    clavesPresentes: string[]
  ): Ronda[] {

    const resultado: Ronda[] = [];
    let ultimaRonda = rondasExistentes[rondasExistentes.length - 1];

    // Si la última ronda ya es la final o solo hay 1 combate sin BYE pendiente, paramos
    const claveUltima = ordenClaves[ordenClaves.indexOf(
      clavesPresentes[clavesPresentes.length - 1]
    )];

    // Extraer ganadores de la última ronda (para construir la siguiente)
    let ganadores = this.extraerGanadores(ultimaRonda.combates);

    // Si no hay al menos 2 ganadores para hacer una ronda siguiente, terminamos
    while (ganadores.length >= 2) {
      const claveUltimaActual = clavesPresentes[clavesPresentes.length - 1 + resultado.length] ??
        ordenClaves[Math.min(
          ordenClaves.indexOf(clavesPresentes[clavesPresentes.length - 1]) + 1 + resultado.length,
          ordenClaves.length - 1
        )];

      const idxSiguiente = ordenClaves.indexOf(claveUltimaActual) + 1;
      if (idxSiguiente >= ordenClaves.length) break;

      const claveNueva = ordenClaves[idxSiguiente];
      const nuevaRondaCombates = this.emparejarCompetidores(ganadores, claveNueva, idxSiguiente + 1);

      const nuevaRonda: Ronda = {
        etiqueta: this.labelRonda(claveNueva),
        tipo:     claveNueva === 'Final' ? 'final' : 'ronda',
        combates: nuevaRondaCombates
      };

      resultado.push(nuevaRonda);

      // Los ganadores de esta nueva ronda para la siguiente iteración
      ganadores = this.extraerGanadores(nuevaRondaCombates);
      ultimaRonda = nuevaRonda;

      // Si ya llegamos a la final (1 combate), paramos
      if (nuevaRondaCombates.length <= 1) break;
    }

    return resultado;
  }

  /**
   * Extrae los ganadores de una lista de combates.
   * - Si el combate está finalizado: devuelve el ganador real.
   * - Si el combate es BYE: devuelve el competidorRojo (pasa directo).
   * - Si está pendiente: devuelve el competidorRojo como placeholder.
   */
  private extraerGanadores(combates: Combate[]): Competidor[] {
    const ganadores: Competidor[] = [];
    for (const c of combates) {
      if (c.estado === 'bye') {
        if (c.competidorRojo) ganadores.push(c.competidorRojo);
      } else if (c.estado === 'finalizado') {
        if (c.puntuacionRojo > c.puntuacionAzul && c.competidorRojo) {
          ganadores.push(c.competidorRojo);
        } else if (c.competidorAzul) {
          ganadores.push(c.competidorAzul);
        }
      } else {
        // Pendiente: ponemos placeholder null para mantener el hueco
        // pero solo si hay competidores asignados
        if (c.competidorRojo) ganadores.push(c.competidorRojo);
        else ganadores.push(null as any);
      }
    }
    return ganadores;
  }

  /**
   * Empareja una lista de competidores en combates para la siguiente ronda.
   */
  private emparejarCompetidores(competidores: Competidor[], claveRonda: string, tatami: number): Combate[] {
    const combates: Combate[] = [];
    let numeroCombate = 1;

    for (let i = 0; i < competidores.length; i += 2) {
      const rojo = competidores[i] ?? null;
      const azul = competidores[i + 1] ?? null;
      const esBye = azul === null && rojo !== null;

      combates.push({
        numeroTatami:  tatami,
        numeroCombate: numeroCombate++,
        ronda:         claveRonda,
        competidorRojo: rojo,
        competidorAzul: azul,
        puntuacionRojo: 0,
        puntuacionAzul: 0,
        senshu:  null,
        estado:  esBye ? 'bye' : 'pendiente'
      });
    }

    return combates;
  }

  /**
   * Genera un bracket completo desde cero (sin combates en BD),
   * a partir de una lista de competidores mezclados aleatoriamente.
   */
  private generarBracketCompleto(inscritos: any[]): Ronda[] {
    const ordenClaves = ['R1', 'R2', 'R3', 'Semifinal', 'Final'];
    const rondas: Ronda[] = [];

    // Convertir inscritos a Competidor
    let competidores: Competidor[] = inscritos.map(ins => this.inscritoACompetidor(ins));

    let claveIdx = 0;

    while (competidores.length >= 1 && claveIdx < ordenClaves.length) {
      const clave = ordenClaves[claveIdx];
      const combates = this.emparejarCompetidores(competidores, clave, 1);

      rondas.push({
        etiqueta: this.labelRonda(clave),
        tipo:     clave === 'Final' ? 'final' : 'ronda',
        combates
      });

      // Para la siguiente ronda usamos los ganadores simulados (los rojos, ya que no hay resultados)
      const ganadores = combates.map(c => c.estado === 'bye' ? c.competidorRojo : c.competidorRojo).filter(Boolean) as Competidor[];

      // Si solo hay 1 combate generado, llegamos a la final
      if (combates.length <= 1) break;

      competidores = ganadores;
      claveIdx++;
    }

    return rondas;
  }

  // ── Helpers privados ──────────────────────────────────────────────────────

  /** Convierte un InscripcionDTO (del backend) a la interfaz Competidor del frontend */
  private inscritoACompetidor(ins: any): Competidor {
    const partes    = (ins.nombreCompetidor ?? '').trim().split(' ');
    const nombre    = partes[0] ?? '';
    const apellidos = partes.slice(1).join(' ');

    return {
      id:                   ins.idCompetidor,
      idUsuario:            ins.idCompetidor,
      nombre,
      apellidos,
      club:                 ins.clubCompetidor ?? '',
      federacionAutonomica: '',
      dni:                  '',
      email:                '',
      rol:                  'COMPETIDOR' as any,
      genero:               'M',
      fechaNacimiento:      ''
    };
  }

  /** Mapea un combate que viene del endpoint /api/combates al formato de la interfaz */
  private mapCombate(c: any): Combate {
    return {
      numeroTatami:  c.id?.numeroTatami  ?? c.numeroTatami  ?? 1,
      numeroCombate: c.id?.numeroCombate ?? c.numeroCombate ?? 1,
      ronda:         c.ronda    ?? 'R1',
      competidorRojo: c.competidorRojo ?? null,
      competidorAzul: c.competidorAzul ?? null,
      puntuacionRojo: c.puntuacionRojo ?? 0,
      puntuacionAzul: c.puntuacionAzul ?? 0,
      senshu:  c.senshu  ?? null,
      estado:  c.estado  ?? 'pendiente'
    };
  }

  /** Etiqueta legible para cada ronda */
  private labelRonda(r: string): string {
    const map: Record<string, string> = {
      R1:        '1ª Ronda',
      R2:        '2ª Ronda',
      R3:        'Cuartos de final',
      Semifinal: 'Semifinal',
      Final:     'Final'
    };
    return map[r] ?? r;
  }

  // ── Helpers para el template ──────────────────────────────────────────────

  nombreCompetidor(comp: Competidor | null): string {
    if (!comp) return 'BYE';
    return `${comp.nombre} ${comp.apellidos}`.trim();
  }

  abrevClub(club: string | undefined): string {
    if (!club) return '—';
    return club.replace(/[^A-ZÁÉÍÓÚÑ]/gi, '').toUpperCase().slice(0, 3);
  }

  rojoGana(c: Combate): boolean {
    return c.estado === 'finalizado' && c.puntuacionRojo > c.puntuacionAzul;
  }

  azulGana(c: Combate): boolean {
    return c.estado === 'finalizado' && c.puntuacionAzul > c.puntuacionRojo;
  }

  trackRonda(_: number, r: Ronda)     { return r.etiqueta; }
  trackCombate(_: number, c: Combate) { return `${c.numeroCombate ?? c.id?.numeroCombate}_${c.numeroTatami ?? c.id?.numeroTatami}`; }
}
