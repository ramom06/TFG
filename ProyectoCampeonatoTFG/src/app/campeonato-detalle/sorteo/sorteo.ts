import { Component, OnInit, signal, inject, computed } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Sorteo} from '../../interfaces/sorteo';
import {Combate} from '../../interfaces/combate';
import {Ronda} from '../../interfaces/ronda';
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
    // Los nombres de parámetro deben coincidir EXACTAMENTE con los de app.routes.ts
    const idC   = Number(this.route.snapshot.paramMap.get('id'));           // :id
    const idCat = Number(this.route.snapshot.paramMap.get('idCategoria'));  // :idCategoria
    this.idCampeonato.set(idC);
    this.idCategoria.set(idCat);

    try {
      const url = `${environment.apiUrl}/api/campeonatos/${idC}/categorias/${idCat}/sorteo`;
      const res = await fetch(url);
      if (!res.ok) throw new Error('No se pudo cargar el sorteo');
      const data: Sorteo = await res.json();
      this.sorteoData.set(data);
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al cargar el sorteo');
    } finally {
      this.loading.set(false);
    }
  }

  nombreCompetidor(comp: Competidor | null): string {
    if (!comp) return 'BYE';
    return `${comp.nombre} ${comp.apellidos}`;
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

  trackRonda(_: number, r: Ronda) { return r.numero; }
  trackCombate(_: number, c: Combate) { return `${c.numeroCombate}_${c.numeroTatami}`; }
}
