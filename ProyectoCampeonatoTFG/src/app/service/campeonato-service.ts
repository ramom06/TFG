import { Injectable } from '@angular/core';
import { CampeonatoProvider } from '../provider/campeonato-provider';
import { Campeonato } from '../interfaces/campeonato';

@Injectable({
  providedIn: 'root',
})
export class CampeonatoService implements CampeonatoProvider {

  private readonly apiUrl = 'http://localhost:8080/api/campeonatos';

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

  async createCampeonato(campeonato: Omit<Campeonato, 'id_campeonato'>): Promise<Campeonato> {
    const response = await fetch(this.apiUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(campeonato),
    });
    if (!response.ok) throw new Error('Error al crear el campeonato');
    return await response.json();
  }

  async updateCampeonato(id: number, campeonato: Omit<Campeonato, 'id_campeonato'>): Promise<Campeonato> {
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
}
