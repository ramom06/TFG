import { Injectable } from '@angular/core';
import { CampeonatoProvider } from '../provider/campeonato-provider';
import { Campeonato } from '../interfaces/campeonato';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CampeonatoService implements CampeonatoProvider {

private readonly apiUrl       = `${environment.apiUrl}/api/campeonatos`;
private readonly sorteosUrl   = `${environment.apiUrl}/api/sorteos`;

  async getAllCampeonatos(): Promise<Campeonato[]> {
    const response = await fetch(this.apiUrl);
    if (!response.ok) throw new Error('API fuera de servicio');
    return await response.json();
  }

  async getCampeonato(id: number): Promise<Campeonato> {
    const response = await fetch(`${this.apiUrl}/${id}`);
    if (!response.ok) throw new Error(`Campeonato con id ${id} no encontrado`);
    return await response.json();
  }

  async createCampeonato(campeonato: Omit<Campeonato, 'idCampeonato'>): Promise<Campeonato> {
    const response = await fetch(this.apiUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(campeonato),
    });
    if (!response.ok) throw new Error('Error al crear el campeonato');
    return await response.json();
  }

  async updateCampeonato(id: number, campeonato: Omit<Campeonato, 'idCampeonato'>): Promise<Campeonato> {
    const response = await fetch(`${this.apiUrl}/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(campeonato),
    });
    if (!response.ok) throw new Error('Error al actualizar el campeonato');
    return await response.json();
  }

  async deleteCampeonato(id: number): Promise<void> {
    const response = await fetch(`${this.apiUrl}/${id}`, {
      method: 'DELETE',
    });
    if (!response.ok) throw new Error('Error al eliminar el campeonato');
  }

  // Sortea solo la primera ronda del campeonato.
  async sortearPrimeraRonda(id: number): Promise<Campeonato> {
    const response = await fetch(`${this.sorteosUrl}/${id}/sortear-primera-ronda`, {
      method: 'POST',
    });
    if (!response.ok) throw new Error('Error al generar la primera ronda');
    return await response.json();
  }

  // Sortea el campeonato completo hasta el ganador.
  async sortearCompleto(id: number): Promise<Campeonato> {
    const response = await fetch(`${this.sorteosUrl}/${id}/sortear-completo`, {
      method: 'POST',
    });
    if (!response.ok) throw new Error('Error al generar el sorteo completo');
    return await response.json();
  }
}
