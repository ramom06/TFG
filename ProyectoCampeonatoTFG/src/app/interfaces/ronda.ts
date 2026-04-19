import { Combate } from './combate';

export interface Ronda {
  etiqueta: string;
  tipo: 'ronda' | 'repesca' | 'final';
  combates: Combate[];
}
