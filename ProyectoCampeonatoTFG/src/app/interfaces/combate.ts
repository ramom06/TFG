import {Competidor} from './competidor';

export interface Combate {
  id: CombateId;
  ronda: string;
  estado: string;
  puntuacionRojo: number;
  puntuacionAzul: number;
  senshu: string | null;
  observaciones: string | null;
  competidorRojo: Competidor;
  competidorAzul: Competidor | null;
}

export interface CombateId {
  idCampeonato: number;
  idCategoria: number;
  numeroTatami: number;
  numeroCombate: number;
}
