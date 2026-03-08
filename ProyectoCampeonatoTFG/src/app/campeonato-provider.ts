import {Campeonato} from './campeonato';

export interface CampeonatoProvider {
  getAllCampeonatos(): Promise<Campeonato[]>;
}
