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

  // Hay sorteo persistido cuando el backend devuelve combates
  hayCombates = computed(() => (this.sorteoData()?.rondas.length ?? 0) > 0);

  rondasVisibles = computed(() => this.sorteoData()?.rondas ?? []);

  ganador = computed(() => {
    const s = this.sorteoData();
    if (!s) return null;
    const rondas = s.rondas;
    if (rondas.length === 0) return null;
    const ultimaRonda = rondas[rondas.length - 1];
    if (ultimaRonda.combates.length === 0) return null;
    const combFinal = ultimaRonda.combates[0];
    if (combFinal.estado !== 'finalizado') return null;
    // Bye en final: el rojo es el ganador
    if (!combFinal.competidorAzul) return combFinal.competidorRojo;
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
      const inscritos: any[] = resIns.ok ? await resIns.json() : [];

      const resComb = await fetch(
        `${environment.apiUrl}/api/combates/campeonato/${idC}/categoria/${idCat}`
      );
      const combates: any[] = resComb.ok ? await resComb.json() : [];

      const resCamp = await fetch(`${environment.apiUrl}/api/campeonatos/${idC}`);
      const campData = resCamp.ok ? await resCamp.json() : null;
      this.estadoCampeonato.set(campData?.estado ?? '');

      const nombreCategoria  = inscritos[0]?.nombreCategoria  ?? '';
      const nombreCampeonato = inscritos[0]?.nombreCampeonato ?? campData?.nombre ?? '';

      const rondas = combates.length > 0
        ? this.construirBracketDesdeCombates(combates)
        : [];

      this.sorteoData.set({ idCampeonato: idC, idCategoria: idCat, nombreCategoria, nombreCampeonato, rondas });

    } catch (e: any) {
      this.error.set(e.message ?? 'Error al cargar el sorteo');
    } finally {
      this.loading.set(false);
    }
  }

  // ── Construcción del bracket a partir de los combates del backend ─────────

  // Orden de menor a mayor (más combates a menos): primera ronda primero, final al final.
  private readonly ORDEN_RONDAS = ['dieciseisavos', 'octavos', 'cuartos', 'semifinal', 'final'];

  private construirBracketDesdeCombates(combates: any[]): Ronda[] {
    const porClave = new Map<string, any[]>();
    for (const c of combates) {
      const key = (c.ronda ?? '').toLowerCase();
      if (!porClave.has(key)) porClave.set(key, []);
      porClave.get(key)!.push(c);
    }

    // Tomamos solo las claves conocidas que estén presentes, en orden de bracket
    const clavesPresentes = this.ORDEN_RONDAS.filter(k => porClave.has(k));
    if (clavesPresentes.length === 0) return [];

    return clavesPresentes.map(clave => {
      const combatesRonda = porClave.get(clave)!
        .sort((a, b) => (a.idCombate?.numeroCombate ?? 0) - (b.idCombate?.numeroCombate ?? 0))
        .map(c => this.mapCombate(c));
      return {
        etiqueta: this.labelRonda(clave),
        tipo:     clave === 'final' ? 'final' : 'ronda',
        combates: combatesRonda,
      };
    });
  }

  private mapCombate(c: any): Combate {
    const esBye = !c.competidorAzul && c.competidorRojo;
    return {
      idCombate: {
        idCampeonato: c.idCombate?.idCampeonato ?? 0,
        idCategoria:  c.idCombate?.idCategoria  ?? 0,
      },
      ronda:          c.ronda          ?? '',
      competidorRojo: c.competidorRojo ?? null,
      competidorAzul: c.competidorAzul ?? null,
      puntuacionRojo: c.puntuacionRojo ?? 0,
      puntuacionAzul: c.puntuacionAzul ?? 0,
      estado:         esBye ? 'bye' : (c.estado ?? 'pendiente'),
    };
  }

  private labelRonda(r: string): string {
    const map: Record<string, string> = {
      dieciseisavos: 'Dieciseisavos',
      octavos:       'Octavos',
      cuartos:       'Cuartos de final',
      semifinal:     'Semifinal',
      final:         'Final',
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
    if (c.estado !== 'finalizado') return false;
    if (!c.competidorAzul) return true; // bye: rojo gana
    return c.puntuacionRojo > c.puntuacionAzul;
  }

  azulGana(c: Combate): boolean {
    if (c.estado !== 'finalizado') return false;
    if (!c.competidorAzul) return false;
    return c.puntuacionAzul > c.puntuacionRojo;
  }

  trackRonda(_: number, r: Ronda)         { return r.etiqueta; }
  trackCombate(index: number, _: Combate) { return index; }
}
