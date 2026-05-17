import { Injectable } from '@angular/core';
import { Combate } from '../interfaces/combate';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class CombateService {

  private readonly apiUrl = `${environment.apiUrl}/api/combates`;

  async getCombatesPorCategoriaYCampeonato(idCampeonato: number, idCategoria: number): Promise<Combate[]> {
    const response = await fetch(`${this.apiUrl}/campeonato/${idCampeonato}/categoria/${idCategoria}`);
    if (!response.ok) throw new Error('Error al cargar los combates');
    return await response.json();
  }
}
