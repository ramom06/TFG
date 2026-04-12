import { Injectable, signal } from '@angular/core';
import { CompetidorSesion } from '../interfaces/competidor-session';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })

//
export class CompetidorAuthService {

  private readonly apiUrl = `${environment.apiUrl}/api/usuarios`;
  private readonly KEY      = 'competidor_session';

  //Mantiene sesion
  currentCompetidor = signal<CompetidorSesion | null>(this.loadSession());

  // Login con DNI + password
  async login(dni: string, password: string): Promise<CompetidorSesion> {
    let res: Response;

    try {
      res = await fetch(`${this.apiUrl}/login-competidor`, {
        method:  'POST',
        headers: { 'Content-Type': 'application/json' },
        body:    JSON.stringify({ dni, password }),
      });
    } catch {
      throw new Error('No se puede conectar con el servidor. ¿Está el backend arrancado?');
    }

    if (res.status === 401) throw new Error('DNI o contraseña incorrectos');
    if (res.status === 403) throw new Error('Esta cuenta no es de competidor');
    if (!res.ok)            throw new Error('Error inesperado del servidor');

    const data = await res.json();

    const sesion: CompetidorSesion = {
      id:              data.id ?? data.idUsuario,
      nombre:          data.nombre,
      apellidos:       data.apellidos ?? '',
      email:           data.email,
      rol:             data.rol,
      genero:          data.genero,
      fechaNacimiento: data.fechaNacimiento,
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
    try {
      return s ? JSON.parse(s) : null;
    } catch {
      return null;
    }
  }
}
