import { Injectable, signal } from '@angular/core';
import { CompetidorSesion } from '../interfaces/inscripcion-form';

@Injectable({ providedIn: 'root' })
export class CompetidorAuthService {

  private readonly apiUrl   = 'http://localhost:8080/api/usuarios';
  private readonly KEY      = 'competidor_session';

  currentCompetidor = signal<CompetidorSesion | null>(this.loadSession());

  /** Login con DNI + password: reutiliza el mismo endpoint /api/usuarios/login */
  async login(dni: string, password: string): Promise<CompetidorSesion> {
    const res = await fetch(`${this.apiUrl}/login`, {
      method:  'POST',
      headers: { 'Content-Type': 'application/json' },
      body:    JSON.stringify({ dni, password }),
    });

    if (res.status === 401) throw new Error('DNI o contraseña incorrectos');
    if (res.status === 403) throw new Error('Esta cuenta no tiene acceso como competidor');
    if (!res.ok)            throw new Error('No se puede conectar con el servidor');

    const data = await res.json();

    // Aceptamos rol COMPETIDOR (o ADMIN durante pruebas)
    const sesion: CompetidorSesion = {
      id:       data.id,
      nombre:   data.nombre,
      apellidos: data.apellidos ?? '',
      email:    data.email,
      rol:      data.rol,
    };
    this.saveSession(sesion);
    this.currentCompetidor.set(sesion);
    return sesion;
  }

  logout(): void {
    sessionStorage.removeItem(this.KEY);
    this.currentCompetidor.set(null);
  }

  isLoggedIn(): boolean {
    return this.currentCompetidor() !== null;
  }

  private saveSession(u: CompetidorSesion) {
    sessionStorage.setItem(this.KEY, JSON.stringify(u));
  }

  private loadSession(): CompetidorSesion | null {
    const s = sessionStorage.getItem(this.KEY);
    return s ? JSON.parse(s) : null;
  }
}
