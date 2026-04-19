import {Ronda} from './ronda';

export interface Sorteo {
  idCampeonato: number;
  idCategoria: number;
  nombreCategoria: string;
  nombreCampeonato: string;
  rondas: Ronda[];
}
