import { Injectable } from '@angular/core';
import { Inscripcion }  from '../interfaces/inscripcion';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class InscripcionCompetidorService {

  private readonly apiUrl = `${environment.apiUrl}/api/inscripciones`;

  // Inscribe al competidor en una categoría de un campeonato
  async inscribir(idCampeonato: number, idCategoria: number, idCompetidor: number): Promise<Inscripcion> {
    const res = await fetch(`${this.apiUrl}/${idCampeonato}/${idCategoria}/${idCompetidor}`, {
      method: 'POST',
    });
    if (res.status === 409) throw new Error('Ya estás inscrito en esta categoría');
    if (!res.ok) throw new Error('Error al procesar la inscripción');
    return res.json();
  }
  // Cancela una inscripción
  async cancelar(idCampeonato: number, idCategoria: number, idCompetidor: number): Promise<void> {
    const res = await fetch(`${this.apiUrl}/${idCampeonato}/${idCategoria}/${idCompetidor}`, {
      method: 'DELETE',
    });
    if (!res.ok) throw new Error('Error al cancelar la inscripción');
  }

  // Obtiene todas las inscripciones de un competidor
  async getMisInscripciones(idCompetidor: number): Promise<Inscripcion[]> {
    const res = await fetch(`${this.apiUrl}/competidor/${idCompetidor}`);
    if (!res.ok) throw new Error('Error al cargar inscripciones');
    return res.json();
  }
}
