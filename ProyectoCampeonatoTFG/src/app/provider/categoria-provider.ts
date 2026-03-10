import {Categoria} from '../interfaces/categoria';

export interface CategoriaProvider {
  getCategoriasPorCampeonato: (idCampeonato: number) => Promise<Categoria[]>;
}
