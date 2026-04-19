export interface Usuario {
  idUsuario: number;
  nombre: string;
  apellidos: string;
  dni: string;
  email: string;
  password?: string;
  rol: 'ADMIN' | 'ARBITRO' | 'COMPETIDOR';
  fechaNacimiento: string; // ISO date string: "1990-05-15"
  genero: 'M' | 'F';
  fechaRegistro?: string;
}
