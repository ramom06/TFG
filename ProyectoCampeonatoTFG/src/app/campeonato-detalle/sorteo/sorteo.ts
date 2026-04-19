import {Component, computed, inject, OnInit, signal} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {environment} from '../../../environments/environment';
import {Ronda} from '../../interfaces/ronda';
import {Combate} from '../../interfaces/combate';

@Component({
  selector: 'app-sorteo',
  imports: [],
  templateUrl: './sorteo.html',
  styleUrl: './sorteo.css',
})
export class Sorteo implements OnInit {
  private route = inject(ActivatedRoute);

  sorteo      = signal<Sorteo | null>(null);
  loading     = signal(true);
  error       = signal<string | null>(null);

  idCampeonato = signal(0);
  idCategoria  = signal(0);

  /** Ganador del torneo: competidor que ganó la Final */
  ganador = computed(() => {
    const s = this.sorteo();
    if (!s) return null;
    const final = s.rondas.find(r => r.nombre === 'Final');
    if (!final || final.combates.length === 0) return null;
    const combFinal = final.combates[0];
    if (combFinal.estado !== 'finalizado') return null;
    if (combFinal.puntuacionRojo > combFinal.puntuacionAzul) return combFinal.competidorRojo;
    if (combFinal.puntuacionAzul > combFinal.puntuacionRojo) return combFinal.competidorAzul;
    return null;
  });

  async ngOnInit() {
    const idC  = Number(this.route.snapshot.paramMap.get('id'));
    const idCat = Number(this.route.snapshot.paramMap.get('idCategoria'));
    this.idCampeonato.set(idC);
    this.idCategoria.set(idCat);

    try {
      const url = `${environment.apiUrl}/api/campeonatos/${idC}/categorias/${idCat}/sorteo`;
      const res = await fetch(url);
      if (!res.ok) throw new Error('No se pudo cargar el sorteo');
      const data: Sorteo = await res.json();
      this.sorteo.set(data);
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al cargar el sorteo');
    } finally {
      this.loading.set(false);
    }
  }

  // ── Helpers para el template ──────────────────────────────────────────────

  /** Nombre completo del competidor o "BYE" */
  nombreCompetidor(comp: { nombre: string; apellidos: string } | null): string {
    if (!comp) return 'BYE';
    return `${comp.nombre} ${comp.apellidos}`;
  }

  /** Abreviatura del club (máx 3 letras para el badge) */
  abrevClub(club: string | undefined): string {
    if (!club) return '—';
    return club.replace(/[^A-ZÁÉÍÓÚÑ]/gi, '')
      .toUpperCase()
      .slice(0, 3);
  }

  /** Clases CSS según estado del combate */
  estadoClass(combate: Combate): string {
    switch (combate.estado) {
      case 'finalizado': return 'finalizado';
      case 'bye':        return 'bye';
      default:           return 'pendiente';
    }
  }

  /** Determina si el competidor rojo ganó */
  rojoGana(c: Combate): boolean {
    return c.estado === 'finalizado' && c.puntuacionRojo > c.puntuacionAzul;
  }

  /** Determina si el competidor azul ganó */
  azulGana(c: Combate): boolean {
    return c.estado === 'finalizado' && c.puntuacionAzul > c.puntuacionRojo;
  }

  trackRonda(_: number, r: Ronda) { return r.numero; }
  trackCombate(_: number, c: Combate) { return c.numeroCombate + '_' + c.numeroTatami; }
}
