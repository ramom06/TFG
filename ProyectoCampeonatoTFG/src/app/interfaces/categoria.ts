import {Genero} from './campeonato_categoria';

export interface Categoria {
  idCategoria: number,
  nombre: string,
  modalidad: Modalidad,
  genero: Genero,
  pesoMinimo?: number,
  pesoMaximo?: number,
  edadMinima: number,
  edadMaxima: number
}

export type Modalidad = 'Kumite' | "Kata";
