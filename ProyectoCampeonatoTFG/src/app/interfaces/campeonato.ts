export type Nivel  = 'regional' | 'nacional' | 'provincial';
// Estados crudos que puede devolver el backend
export type Estado = 'futuro' | 'activo' | 'inscripciones_cerradas' | 'pasado';
// Estados visuales (los que se muestran en la UI tras calcular por fechas)
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
