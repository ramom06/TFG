import { Competidor } from './competidor';

export interface Combate {
  id?: CombateId;           // opcional — viene del backend
  numeroTatami?: number;    // usado en sorteo (generado en frontend)
  numeroCombate?: number;   // usado en sorteo (generado en frontend)
  ronda: string;
  estado: string;
  puntuacionRojo: number;
  puntuacionAzul: number;
  senshu: string | null;
  observaciones?: string | null;
  competidorRojo: Competidor;
  competidorAzul: Competidor | null;
}

export interface CombateId {
  idCampeonato: number;
  idCategoria: number;
  numeroTatami: number;
  numeroCombate: number;
}
