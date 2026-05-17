import { Competidor } from './competidor';
import {Combate_id} from './combate_id';

export interface Combate {
  idCombate: Combate_id;
  ronda: string;
  estado: string;
  puntuacionRojo: number;
  puntuacionAzul: number;
  competidorRojo: Competidor | null;
  competidorAzul: Competidor | null;
}


