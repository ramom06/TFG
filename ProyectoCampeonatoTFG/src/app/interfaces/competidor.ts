import { Usuario } from './usuario';

export interface Competidor extends Usuario {
  club: string;
  federacionAutonomica: string;
}
