import { Component, OnInit, signal, inject, computed } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Sorteo } from '../interfaces/sorteo';
import { Combate } from '../interfaces/combate';
import { Ronda } from '../interfaces/ronda';
import { Competidor } from '../interfaces/competidor';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-sorteo',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './sorteo.html',
  styleUrl: './sorteo.css',
})
export class SorteoComponent implements OnInit {
  private route = inject(ActivatedRoute);

  sorteoData       = signal<Sorteo | null>(null);
  loading          = signal(true);
  error            = signal<string | null>(null);
  estadoCampeonato = signal<string>('');

  idCampeonato = signal(0);
  idCategoria  = signal(0);

  rondasVisibles = computed(() => {
    const s = this.sorteoData();
    if (!s) return [];
    if (this.estadoCampeonato() === 'futuro') {
      return s.rondas.slice(0, 1);
    }
    return s.rondas;
  });

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

      const resCamp = await fetch(`${environment.apiUrl}/api/campeonatos/${idC}`);
      const campData = resCamp.ok ? await resCamp.json() : null;
      this.estadoCampeonato.set(campData?.estado ?? '');

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

    if (combates.length > 0) {
      const rondas = this.construirBracketDesdeCombates(combates);
      return { idCampeonato, idCategoria, nombreCategoria, nombreCampeonato, rondas };
    }

    if (inscritos.length === 0) {
      return { idCampeonato, idCategoria, nombreCategoria, nombreCampeonato, rondas: [] };
    }

    const competidores = [...inscritos].sort(() => Math.random() - 0.5);
    const rondas = this.generarBracketCompleto(competidores, idCampeonato, idCategoria);
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
        tipo:     clave === 'Final' || clave === 'F'      ? 'final'
          : clave === 'REP' || clave === 'REPESCA'  ? 'repesca'
            : 'ronda',
        combates: porClave.get(clave)!.map(c => this.mapCombate(c))
      });
    }

    return rondas;
  }

  private generarBracketCompleto(inscritos: any[], idCampeonato: number, idCategoria: number): Ronda[] {
    const ordenClaves = ['R1', 'R2', 'R3', 'Semifinal', 'Final'];
    const rondas: Ronda[] = [];
    let competidores: Competidor[] = inscritos.map(ins => this.inscritoACompetidor(ins));
    let claveIdx = 0;

    while (competidores.length >= 1 && claveIdx < ordenClaves.length) {
      const clave    = ordenClaves[claveIdx];
      const combates = this.emparejarCompetidores(competidores, clave, idCampeonato, idCategoria);
      rondas.push({
        etiqueta: this.labelRonda(clave),
        tipo:     clave === 'Final' ? 'final' : 'ronda',
        combates
      });
      if (combates.length <= 1) break;
      competidores = combates.map(c => c.competidorRojo).filter(Boolean) as Competidor[];
      claveIdx++;
    }

    return rondas;
  }

  // ── Helpers privados ──────────────────────────────────────────────────────

  /** Genera combates locales (sin id de BD) para el bracket provisional */
  private emparejarCompetidores(competidores: Competidor[], claveRonda: string, idCampeonato: number, idCategoria: number): Combate[] {
    const combates: Combate[] = [];
    for (let i = 0; i < competidores.length; i += 2) {
      const rojo  = competidores[i]     ?? null;
      const azul  = competidores[i + 1] ?? null;
      const esBye = azul === null && rojo !== null;
      combates.push({
        idCombate:      { idCampeonato, idCategoria },
        ronda:          claveRonda,
        competidorRojo: rojo,
        competidorAzul: azul,
        puntuacionRojo: 0,
        puntuacionAzul: 0,
        estado:         esBye ? 'bye' : 'pendiente'
      });
    }
    return combates;
  }

  private inscritoACompetidor(ins: any): Competidor {
    const partes    = (ins.nombreCompetidor ?? '').trim().split(' ');
    const nombre    = partes[0] ?? '';
    const apellidos = partes.slice(1).join(' ');
    return {
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
      idCombate: {
        idCampeonato: c.idCombate?.idCampeonato ?? 0,
        idCategoria:  c.idCombate?.idCategoria  ?? 0,
      },
      ronda:          c.ronda          ?? 'R1',
      competidorRojo: c.competidorRojo ?? null,
      competidorAzul: c.competidorAzul ?? null,
      puntuacionRojo: c.puntuacionRojo ?? 0,
      puntuacionAzul: c.puntuacionAzul ?? 0,
      estado:         c.estado         ?? 'pendiente'
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

  trackRonda(_: number, r: Ronda)                        { return r.etiqueta; }
  trackCombate(index: number, _: Combate) { return index; }
}
