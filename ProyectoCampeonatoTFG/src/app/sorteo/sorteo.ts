import { Component, OnInit, signal, computed, inject } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CampeonatoService }  from '../service/campeonato-service';
import { CategoriaService }   from '../service/categoria-service';
import { InscripcionService } from '../service/inscripcion-service';
import { Campeonato }   from '../interfaces/campeonato';
import { Categoria }    from '../interfaces/categoria';
import { Inscripcion }  from '../interfaces/inscripcion';

export interface MatchPlayer {
  name: string;
  club: string;
  isWinner: boolean;
  isBye: boolean;
  score: string;
}

export interface Match {
  rojo: MatchPlayer;
  azul: MatchPlayer;
}

export interface BracketRound {
  label: string;
  matches: Match[];
  spacer: number;
}

@Component({
  selector: 'app-sorteo',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './sorteo.html',
  styleUrl: './sorteo.css',
})
export class SorteoComponent implements OnInit {
  private route    = inject(ActivatedRoute);
  private router   = inject(Router);
  private campSvc  = inject(CampeonatoService);
  private catSvc   = inject(CategoriaService);
  private inscSvc  = inject(InscripcionService);

  campeonato  = signal<Campeonato | null>(null);
  categoria   = signal<Categoria | null>(null);
  inscritos   = signal<Inscripcion[]>([]);
  loading     = signal(true);
  error       = signal<string | null>(null);

  idCampeonato = 0;
  idCategoria  = 0;

  rounds = signal<BracketRound[]>([]);
  winner = signal<string>('');

  async ngOnInit(): Promise<void> {
    this.idCampeonato = Number(this.route.snapshot.paramMap.get('idCampeonato'));
    this.idCategoria  = Number(this.route.snapshot.paramMap.get('idCategoria'));

    try {
      const [campeonatos, categorias, inscritos] = await Promise.all([
        this.campSvc.getAllCampeonatos(),
        this.catSvc.getCategoriasPorCampeonato(this.idCampeonato),
        this.inscSvc.getInscritosPorCategoria(this.idCampeonato, this.idCategoria),
      ]);

      const camp = campeonatos.find(c => c.id_campeonato === this.idCampeonato) ?? null;
      const cat  = categorias.find(c => c.id_categoria  === this.idCategoria)  ?? null;

      if (!camp) { this.error.set('Campeonato no encontrado'); return; }
      if (!cat)  { this.error.set('Categoría no encontrada'); return; }
      if (camp.estado === 'futuro') {
        this.error.set('El sorteo solo está disponible para campeonatos iniciados o finalizados.');
        return;
      }

      this.campeonato.set(camp);
      this.categoria.set(cat);
      this.inscritos.set(inscritos);
      this.buildBracket(inscritos);
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al cargar el sorteo');
    } finally {
      this.loading.set(false);
    }
  }

  private buildBracket(inscritos: Inscripcion[]): void {
    if (inscritos.length === 0) return;

    // Determinar el tamaño del bracket (siguiente potencia de 2)
    const size = this.nextPowerOf2(inscritos.length);
    const seed = this.deterministicShuffle(inscritos);

    // Rellenar con BYEs hasta completar el bracket
    const players: (Inscripcion | null)[] = [...seed];
    while (players.length < size) players.push(null);

    // Generar rondas completas de forma determinista
    const allRounds: BracketRound[] = [];
    let current = players;
    let roundNum = size;

    while (roundNum > 1) {
      const matches: Match[] = [];
      const nextCurrent: (Inscripcion | null)[] = [];
      const half = roundNum / 2;

      for (let i = 0; i < current.length; i += 2) {
        const a = current[i];
        const b = current[i + 1] ?? null;

        const isByeA = a === null;
        const isByeB = b === null;

        // El ganador determinista es: si hay BYE gana el real; si ambos reales, gana por hash deterministico
        let winner: Inscripcion | null;
        if (isByeA)       winner = b;
        else if (isByeB)  winner = a;
        else              winner = this.deterministicWinner(a, b);

        const matchWinnerIsA = winner === a;

        matches.push({
          rojo: {
            name:      a ? a.nombreCompetidor : '',
            club:      a ? a.clubCompetidor   : '',
            isWinner:  !isByeA && !isByeB && matchWinnerIsA,
            isBye:     isByeA,
            score:     isByeA ? '' : (isByeB ? 'BYE' : (matchWinnerIsA ? '8*' : '0')),
          },
          azul: {
            name:      b ? b.nombreCompetidor : '',
            club:      b ? b.clubCompetidor   : '',
            isWinner:  !isByeA && !isByeB && !matchWinnerIsA,
            isBye:     isByeB,
            score:     isByeB ? 'BYE' : (isByeA ? '' : (!matchWinnerIsA ? '8*' : '0')),
          },
        });

        nextCurrent.push(winner);
      }

      const label = this.roundLabel(roundNum, size);
      const spacer = roundNum === size ? 0 : (size / roundNum) - 1;

      allRounds.push({ label, matches, spacer });
      current = nextCurrent;
      roundNum = half;
    }

    const finalWinner = current[0];
    this.winner.set(finalWinner ? finalWinner.nombreCompetidor : '');
    this.rounds.set(allRounds);
  }

  private roundLabel(roundNum: number, size: number): string {
    const fraction = roundNum / 2;
    if (fraction === 1)       return 'Final';
    if (fraction === 2)       return 'Semifinal';
    if (fraction === 4)       return 'Cuartos';
    return `Ronda 1/${fraction}`;
  }

  private nextPowerOf2(n: number): number {
    let p = 1;
    while (p < n) p <<= 1;
    return p;
  }

  /** Baraja determinista basada en IDs para que el sorteo sea siempre el mismo */
  private deterministicShuffle(players: Inscripcion[]): Inscripcion[] {
    return [...players].sort((a, b) => {
      const ha = this.hash(a.idCompetidor);
      const hb = this.hash(b.idCompetidor);
      return ha - hb;
    });
  }

  private deterministicWinner(a: Inscripcion, b: Inscripcion): Inscripcion {
    return this.hash(a.idCompetidor) < this.hash(b.idCompetidor) ? a : b;
  }

  private hash(n: number): number {
    let h = n ^ 0xdeadbeef;
    h = Math.imul(h ^ (h >>> 16), 0x45d9f3b);
    h = Math.imul(h ^ (h >>> 16), 0x45d9f3b);
    return (h ^ (h >>> 16)) >>> 0;
  }

  /** Índice de la marca del match (espaciado entre bloques) */
  matchMarginTop(roundIdx: number, matchIdx: number): string {
    const size = this.nextPowerOf2(this.inscritos().length);
    const roundNum = size >> roundIdx;
    const baseH = 68; // altura aproximada de un match en px
    if (matchIdx === 0) {
      return roundIdx === 0 ? '0px' : ((Math.pow(2, roundIdx) - 1) * baseH) + 'px';
    }
    return ((Math.pow(2, roundIdx + 1) - 1) * baseH) + 'px';
  }

  volver(): void {
    this.router.navigate(['/campeonato', this.idCampeonato]);
  }

  get generoLabel(): string {
    return this.categoria()?.genero === 'M' ? 'Masculino' : 'Femenino';
  }
  get generoClass(): string {
    return this.categoria()?.genero === 'M' ? 'badge-m' : 'badge-f';
  }
  get modalidadLabel(): string {
    const m = this.categoria()?.modalidad ?? '';
    return m.charAt(0).toUpperCase() + m.slice(1);
  }
}
