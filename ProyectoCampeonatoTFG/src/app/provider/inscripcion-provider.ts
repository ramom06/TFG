import {Inscripcion} from '../interfaces/inscripcion';

//Este metodo devuelve los competidores que hay en una categoria especifica en un campeonato
export interface InscripcionProvider {
  getInscritosPorCategoria(IdCampeonato:number, IdCategoria:number): Promise<Inscripcion[]>;
}
