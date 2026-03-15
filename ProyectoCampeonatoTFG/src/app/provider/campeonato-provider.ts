import { Campeonato } from '../interfaces/campeonato';

export interface CampeonatoProvider {
  getAllCampeonatos(): Promise<Campeonato[]>;
  getCampeonato(id: number): Promise<Campeonato>;
  createCampeonato(campeonato: Omit<Campeonato, 'id_campeonato'>): Promise<Campeonato>;
  updateCampeonato(id: number, campeonato: Omit<Campeonato, 'id_campeonato'>): Promise<Campeonato>;
  deleteCampeonato(id: number): Promise<void>;
}
