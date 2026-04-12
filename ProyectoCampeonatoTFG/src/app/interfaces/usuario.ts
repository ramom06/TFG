export enum Rol {
  ADMIN = 'ADMIN',
  ARBITRO = 'ARBITRO',
  COMPETIDOR = 'COMPETIDOR'
}

export interface Usuario {
  idUsuario: number;
  nombre: string;
  apellidos: string;
  dni: string;
  email: string;
  rol: Rol;
  genero: 'M' | 'F';
  fechaNacimiento: string;
  fechaRegistro?: string;
}
