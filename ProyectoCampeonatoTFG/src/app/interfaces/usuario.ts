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
  password?: string;
  rol: Rol;
  genero: 'M' | 'F';
  fechaNacimiento: string;
  fechaRegistro?: string;
}

export interface UsuarioAuth {
  id: number;
  nombre: string;
  email: string;
  rol: string;
}
