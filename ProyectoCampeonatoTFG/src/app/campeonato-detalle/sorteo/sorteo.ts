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

  sorteoData   = signal<Sorteo | null>(null);
  loading      = signal(true);
  error        = signal<string | null>(null);
  guardando    = signal(false);

  idCampeonato = signal(0);
  idCategoria  = signal(0);

  ganador = computed(() => {
    const s = this.sorteoData();
    if (!s) return null;
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
    await this.cargarSorteo(idC, idCat);
  }

  // ── Carga / recarga del sorteo ────────────────────────────────────────────

  private async cargarSorteo(idC: number, idCat: number) {
    this.loading.set(true);
    try {
      const resIns = await fetch(
        `${environment.apiUrl}/api/inscripciones/campeonato/${idC}/categoria/${idCat}`
      );
      if (!resIns.ok) throw new Error('No se pudieron cargar los inscritos');
      const inscritos: any[] = await resIns.json();

      const resComb = await fetch(
        `${environment.apiUrl}/api/combates/campeonato/${idC}/categoria/${idCat}`
      );
      const combates: any[] = resComb.ok ? await resComb.json() : [];

      const sorteo = this.construirSorteo(idC, idCat, inscritos, combates);
      this.sorteoData.set(sorteo);
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al cargar el sorteo');
    } finally {
      this.loading.set(false);
    }
  }

  // ── Marcar ganador de un combate ──────────────────────────────────────────

  /**
   * Llama al PUT para marcar como finalizado el combate con el ganador indicado.
   * Si el combate ya está finalizado, permite cambiarlo (desmarcar).
   * Después recarga todo el sorteo para que la siguiente ronda se actualice.
   */
  async marcarGanador(combate: Combate, ganador: 'rojo' | 'azul') {
    if (this.guardando()) return;

    // Si ya tiene ese ganador → lo revertimos a pendiente
    const yaEsGanador =
      (ganador === 'rojo' && this.rojoGana(combate)) ||
      (ganador === 'azul' && this.azulGana(combate));

    const nuevoEstado      = yaEsGanador ? 'pendiente' : 'finalizado';
    const puntuacionRojo   = yaEsGanador ? 0 : (ganador === 'rojo' ? 1 : 0);
    const puntuacionAzul   = yaEsGanador ? 0 : (ganador === 'azul' ? 1 : 0);

    const idC   = this.idCampeonato();
    const idCat = this.idCategoria();
    const tat   = combate.numeroTatami   ?? combate.id?.numeroTatami   ?? 1;
    const num   = combate.numeroCombate  ?? combate.id?.numeroCombate  ?? 1;

    const body = {
      idCampeonato:    idC,
      idCategoria:     idCat,
      numeroTatami:    tat,
      numeroCombate:   num,
      idCompetidorRojo: combate.competidorRojo?.idUsuario ?? combate.competidorRojo?.id,
      idCompetidorAzul: combate.competidorAzul?.idUsuario ?? combate.competidorAzul?.id ?? null,
      ronda:           combate.ronda,
      puntuacionRojo,
      puntuacionAzul,
      senshu:          null,
      estado:          nuevoEstado,
      horaProgramada:  '00:00:00',
      horaInicioReal:  '2000-01-01T00:00:00',
      duracionSegundos: 0,
      observaciones:   null,
    };

    this.guardando.set(true);
    try {
      const res = await fetch(
        `${environment.apiUrl}/api/combates/${idC}/${idCat}/${tat}/${num}`,
        {
          method:  'PUT',
          headers: { 'Content-Type': 'application/json' },
          body:    JSON.stringify(body),
        }
      );
      if (!res.ok) throw new Error('Error al guardar el resultado');
      // Recargamos todo el sorteo → la siguiente ronda se reconstruye con el ganador real
      await this.cargarSorteo(idC, idCat);
    } catch (e: any) {
      alert('No se pudo guardar el resultado: ' + (e.message ?? 'error desconocido'));
    } finally {
      this.guardando.set(false);
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

    if (combates.length > 0) {
      const rondas = this.construirBracketDesdeCombates(combates);
      return { idCampeonato, idCategoria, nombreCategoria, nombreCampeonato, rondas };
    }

    if (inscritos.length === 0) {
      return { idCampeonato, idCategoria, nombreCategoria, nombreCampeonato, rondas: [] };
    }

    const competidores = [...inscritos].sort(() => Math.random() - 0.5);
    const rondas = this.generarBracketCompleto(competidores);
    return { idCampeonato, idCategoria, nombreCategoria, nombreCampeonato, rondas };
  }

  private construirBracketDesdeCombates(combates: any[]): Ronda[] {
    const ordenClaves = ['R1', 'R2', 'R3', 'Semifinal', 'Final'];

    const porClave = new Map<string, any[]>();
    for (const c of combates) {
      const key = c.ronda ?? 'R1';
      if (!porClave.has(key)) porClave.set(key, []);
      porClave.get(key)!.push(c);
    }

    const clavesPresentes = ordenClaves.filter(k => porClave.has(k));
    if (clavesPresentes.length === 0) return [];

    const rondas: Ronda[] = [];
    for (const clave of clavesPresentes) {
      rondas.push({
        etiqueta: this.labelRonda(clave),
        tipo:     clave === 'Final' || clave === 'F' ? 'final'
          : clave === 'REP' || clave === 'REPESCA' ? 'repesca'
            : 'ronda',
        combates: porClave.get(clave)!.map(c => this.mapCombate(c))
      });
    }

    const rondasGeneradas = this.generarRondasFaltantes(rondas, ordenClaves, clavesPresentes);
    return [...rondas, ...rondasGeneradas];
  }

  private generarRondasFaltantes(
    rondasExistentes: Ronda[],
    ordenClaves: string[],
    clavesPresentes: string[]
  ): Ronda[] {
    const resultado: Ronda[] = [];
    let ultimaRonda = rondasExistentes[rondasExistentes.length - 1];
    let ganadores = this.extraerGanadores(ultimaRonda.combates);

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
      ganadores = this.extraerGanadores(nuevaRondaCombates);
      ultimaRonda = nuevaRonda;
      if (nuevaRondaCombates.length <= 1) break;
    }

    return resultado;
  }

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
        if (c.competidorRojo) ganadores.push(c.competidorRojo);
        else ganadores.push(null as any);
      }
    }
    return ganadores;
  }

  private emparejarCompetidores(competidores: Competidor[], claveRonda: string, tatami: number): Combate[] {
    const combates: Combate[] = [];
    let numeroCombate = 1;
    for (let i = 0; i < competidores.length; i += 2) {
      const rojo = competidores[i] ?? null;
      const azul = competidores[i + 1] ?? null;
      const esBye = azul === null && rojo !== null;
      combates.push({
        numeroTatami:   tatami,
        numeroCombate:  numeroCombate++,
        ronda:          claveRonda,
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

  private generarBracketCompleto(inscritos: any[]): Ronda[] {
    const ordenClaves = ['R1', 'R2', 'R3', 'Semifinal', 'Final'];
    const rondas: Ronda[] = [];
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
      const ganadores = combates
        .map(c => c.estado === 'bye' ? c.competidorRojo : c.competidorRojo)
        .filter(Boolean) as Competidor[];
      if (combates.length <= 1) break;
      competidores = ganadores;
      claveIdx++;
    }

    return rondas;
  }

  // ── Helpers privados ──────────────────────────────────────────────────────

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

  private mapCombate(c: any): Combate {
    return {
      numeroTatami:   c.id?.numeroTatami  ?? c.numeroTatami  ?? 1,
      numeroCombate:  c.id?.numeroCombate ?? c.numeroCombate ?? 1,
      ronda:          c.ronda    ?? 'R1',
      competidorRojo: c.competidorRojo ?? null,
      competidorAzul: c.competidorAzul ?? null,
      puntuacionRojo: c.puntuacionRojo ?? 0,
      puntuacionAzul: c.puntuacionAzul ?? 0,
      senshu:  c.senshu  ?? null,
      estado:  c.estado  ?? 'pendiente'
    };
  }

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
  trackCombate(_: number, c: Combate) {
    return `${c.numeroCombate ?? c.id?.numeroCombate}_${c.numeroTatami ?? c.id?.numeroTatami}`;
  }
}
