import { Combate } from './combate';

export interface Ronda {
  etiqueta?: string;
  tipo?: 'ronda' | 'final';
  nombre?: string;
  numero?: number;
  combates: Combate[];
}
