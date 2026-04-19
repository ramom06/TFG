import { Combate } from './combate';

export interface Ronda {
  etiqueta?: string;   // usado en campeonato-detalle
  tipo?: 'ronda' | 'repesca' | 'final';  // usado en campeonato-detalle
  nombre?: string;     // usado en sorteo
  numero?: number;     // usado en sorteo
  combates: Combate[];
}
