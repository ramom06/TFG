export type Genero = 'M' | 'F';
export type Modalidad = 'Kumite' | "Kata";

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

