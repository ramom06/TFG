import { Injectable } from '@angular/core';
import {InscripcionProvider} from '../provider/inscripcion-provider';
import {firstValueFrom} from 'rxjs';
import {Inscripcion} from '../interfaces/inscripcion';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root',
})
export class InscripcionService implements InscripcionProvider{

private readonly apiUrl = `${environment.apiUrl}/api/inscripciones`;

  async getInscritosPorCategoria(idCampeonato: number, idCategoria: number): Promise<Inscripcion[]> {
    const response = await fetch(`${this.apiUrl}/campeonato/${idCampeonato}/categoria/${idCategoria}`);
    if (!response.ok) throw new Error('Error al cargar inscripciones');
    return await response.json();
  }

}
