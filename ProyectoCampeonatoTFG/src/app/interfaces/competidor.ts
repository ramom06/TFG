import { Usuario } from './usuario';

export interface Competidor extends Usuario {
  id: number;
  nombre: string;
  apellidos: string;
  club: string;
  federacionAutonomica: string;
}
