export type Nivel  = 'regional' | 'nacional' | 'provincial';
export type Estado = 'futuro' | 'activo' | 'pasado';


export interface Campeonato {
  id_campeonato: number;
  nombre: string;
  fechaInicio: string;
  fechaFin: string;
  ubicacion: string;
  estado: Estado;
  nivel: Nivel;
  descripcion: string | null;
  urlPortada: string;
}
