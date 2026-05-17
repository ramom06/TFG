import {Rol, Usuario} from './usuario';

export interface Competidor extends Usuario {
  club: string;
  federacionAutonomica: string;
}

export interface CompetidorSesion {
  idUsuario: number;
  nombre: string;
  apellidos: string;
  email: string;
  rol: Rol;
  genero: 'M' | 'F';
  fechaNacimiento: string;
  club?: string;
  federacionAutonomica?: string;
}
