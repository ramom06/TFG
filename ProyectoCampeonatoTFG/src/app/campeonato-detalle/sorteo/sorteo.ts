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
    const finalRonda = s.rondas.find(r => r.nombre === 'Final');
    if (!finalRonda || finalRonda.combates.length === 0) return null;
    const combFinal = finalRonda.combates[0];
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

    // Si ya hay combates en BD los usamos agrupados por ronda
    if (combates.length > 0) {
      const ordenRondas = ['R1', 'R2', 'R3', 'Semifinal', 'Final'];
      const porRonda = new Map<string, any[]>();

      for (const c of combates) {
        const key = c.ronda ?? 'R1';
        if (!porRonda.has(key)) porRonda.set(key, []);
        porRonda.get(key)!.push(c);
      }

      const rondas: Ronda[] = ordenRondas
        .filter(r => porRonda.has(r))
        .map((r, i) => ({
          nombre:   this.labelRonda(r),
          numero:   i + 1,
          combates: porRonda.get(r)!.map(c => this.mapCombate(c))
        }));

      return { idCampeonato, idCategoria, nombreCategoria, nombreCampeonato, rondas };
    }

    // Si no hay combates generamos el bracket aleatorio desde las inscripciones
    if (inscritos.length === 0) {
      return { idCampeonato, idCategoria, nombreCategoria, nombreCampeonato, rondas: [] };
    }

    const competidores = [...inscritos].sort(() => Math.random() - 0.5);
    const combatesSorteo: Combate[] = [];
    let numeroCombate = 1;

    for (let i = 0; i < competidores.length; i += 2) {
      const rojo = competidores[i];
      const azul = competidores[i + 1] ?? null;

      combatesSorteo.push({
        numeroTatami:  1,
        numeroCombate: numeroCombate++,
        ronda:         'R1',
        competidorRojo: this.inscritoACompetidor(rojo),
        competidorAzul: azul ? this.inscritoACompetidor(azul) : null,
        puntuacionRojo: 0,
        puntuacionAzul: 0,
        senshu:  null,
        estado:  azul ? 'pendiente' : 'bye'
      });
    }

    return {
      idCampeonato,
      idCategoria,
      nombreCategoria,
      nombreCampeonato,
      rondas: [{
        nombre:   '1ª Ronda',
        numero:   1,
        combates: combatesSorteo
      }]
    };
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

  trackRonda(_: number, r: Ronda)   { return r.numero; }
  trackCombate(_: number, c: Combate) { return `${c.numeroCombate}_${c.numeroTatami}`; }
}
