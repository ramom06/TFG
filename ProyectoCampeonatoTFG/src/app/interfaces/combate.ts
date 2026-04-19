import {Competidor} from './competidor';

export interface Combate {
  numeroTatami: number;
  numeroCombate: number;
  ronda: string;
  competidorRojo: Competidor | null;
  competidorAzul: Competidor | null;
  puntuacionRojo: number;
  puntuacionAzul: number;
  senshu: string | null;
  estado: string;
}
