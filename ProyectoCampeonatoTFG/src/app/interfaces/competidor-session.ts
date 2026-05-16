import { Rol } from './usuario';

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
