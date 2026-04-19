export enum Rol {
  ADMIN      = 'ADMIN',
  ARBITRO    = 'ARBITRO',
  COMPETIDOR = 'COMPETIDOR',
}

export interface Usuario {
  idUsuario: number;
  nombre: string;
  apellidos: string;
  dni: string;
  email: string;
  password?: string;          // solo al crear/editar, nunca viene del backend
  rol: Rol;
  genero: 'M' | 'F';
  fechaNacimiento: string;    // ISO date string p.ej. "1990-05-15"
  fechaRegistro?: string;
}
