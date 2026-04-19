import {Combate} from './combate';

export interface Ronda {
  etiqueta: string;
  tipo: 'ronda' | 'repesca' | 'final';
  nombre?: string;
  numero?: number;
  combates: Combate[];
}
