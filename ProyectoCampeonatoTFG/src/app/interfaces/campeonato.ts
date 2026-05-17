export type Nivel  = 'regional' | 'nacional' | 'provincial';
export type Estado = 'futuro' | 'activo' | 'inscripciones_cerradas' | 'pasado';
export type EstadoVisual = 'futuro' | 'activo' | 'pasado';


export interface Campeonato {
  idCampeonato: number;
  nombre: string;
  fechaInicio: string;
  fechaFin: string;
  ubicacion: string;
  estado: Estado;
  nivel: Nivel;
  descripcion: string | null;
  urlPortada: string;
}
