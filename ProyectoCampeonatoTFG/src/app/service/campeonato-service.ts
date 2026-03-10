import { Injectable } from '@angular/core';
import {CampeonatoProvider} from '../provider/campeonato-provider';
import {Campeonato} from '../interfaces/campeonato';

@Injectable({
  providedIn: 'root',
})
export class CampeonatoService implements  CampeonatoProvider{

  private readonly apiUrl = 'http://localhost:8080/api/campeonatos';

  async getAllCampeonatos(): Promise<Campeonato[]> {
      const response = await fetch(this.apiUrl);
      if (!response.ok) throw new Error('API fuera de servicio');
      return await response.json();
  }
}
