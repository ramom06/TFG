import {Campeonato} from '../interfaces/campeonato';

//Este metodo devuelve los competidores que hay en una categoria especifica en un campeonato
export interface InscripcionProvider {
  getAllInscripciones(): Promise<Campeonato[]>;
}
