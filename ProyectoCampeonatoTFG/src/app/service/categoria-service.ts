import { Injectable } from '@angular/core';
import {CategoriaProvider} from '../provider/categoria-provider';
import { Categoria } from "../interfaces/categoria";

@Injectable({
  providedIn: 'root',
})
export class CategoriaService implements CategoriaProvider {
  private readonly apiUrl = 'http://localhost:8080/api/campeonatos';

  async getCategoriasPorCampeonato(idCampeonato: number): Promise<Categoria[]> {
    const response = await fetch(`${this.apiUrl}/${idCampeonato}/categorias`);
    if (!response.ok) throw new Error('Error al cargar categorías');
    return await response.json();
  }

}
