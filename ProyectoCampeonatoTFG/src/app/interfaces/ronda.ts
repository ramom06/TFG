import {Combate} from './combate';

export interface Ronda {
  nombre: string;
  numero: number;
  combates: Combate[];
}
