import {Inscripcion} from '../interfaces/inscripcion';

export interface InscripcionProvider {
  getInscritosPorCategoria(IdCampeonato:number, IdCategoria:number): Promise<Inscripcion[]>;
}
