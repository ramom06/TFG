import { Usuario } from './usuario';

export interface Competidor extends Usuario {
  id_competidor: number;
  club: string;
  federacionAutonomica: string;
}
