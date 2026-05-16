import { Injectable } from '@angular/core';
import { CampeonatoProvider } from '../provider/campeonato-provider';
import { Campeonato } from '../interfaces/campeonato';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CampeonatoService implements CampeonatoProvider {

private readonly apiUrl = `${environment.apiUrl}/api/campeonatos`;

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

  async cerrarInscripciones(id: number): Promise<Campeonato> {
    const response = await fetch(`${this.apiUrl}/${id}/cerrar-inscripciones`, {
      method: 'POST',
    });
    if (!response.ok) {
      const txt = await response.text();
      throw new Error(this.extraerMensaje(txt) ?? 'Error al cerrar inscripciones');
    }
    return await response.json();
  }

  async desarrollarBracket(id: number): Promise<Campeonato> {
    const response = await fetch(`${this.apiUrl}/${id}/desarrollar-bracket`, {
      method: 'POST',
    });
    if (!response.ok) {
      const txt = await response.text();
      throw new Error(this.extraerMensaje(txt) ?? 'Error al desarrollar el bracket');
    }
    return await response.json();
  }

  private extraerMensaje(txt: string): string | null {
    try { return JSON.parse(txt)?.message ?? null; } catch { return null; }
  }
}
