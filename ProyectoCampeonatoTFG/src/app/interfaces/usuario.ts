export enum Rol {
  ADMIN      = 'ADMIN',
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
  fechaNacimiento: string;
  fechaRegistro?: string;
}
