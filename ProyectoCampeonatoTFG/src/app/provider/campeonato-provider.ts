import {Campeonato} from '../interfaces/campeonato';

export interface CampeonatoProvider {
  getAllCampeonatos(): Promise<Campeonato[]>;
}
