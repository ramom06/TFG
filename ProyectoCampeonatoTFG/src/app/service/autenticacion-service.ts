import { Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import {UsuarioAuth} from '../interfaces/usuario';

@Injectable({ providedIn: 'root' })
export class AutenticacionService {

  private readonly apiUrl = `${environment.apiUrl}/api/usuarios`;

  private readonly SESSION_KEY = 'admin_session';

  //Va a sesion del navegador si hay la guarda si no null
  currentUser = signal<UsuarioAuth | null>(this.loadSession());

  constructor(private router: Router) {}

  async login(dni: string, password: string): Promise<void> {

    //Le envia al login del backend los datos en forma de json
    const response = await fetch(`${this.apiUrl}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ dni, password }),
    });

    if (response.status === 401) throw new Error('Credenciales incorrectas');
    if (response.status === 403) throw new Error('No tienes permisos de administrador');
    if (!response.ok)            throw new Error('No se puede conectar con el servidor');

    //Guarda los datos de la respuesta como modelo UsuarioAuth
    const data: UsuarioAuth = await response.json();
    this.saveSession(data);
    this.currentUser.set(data);
  }

  logout(): void {
    sessionStorage.removeItem(this.SESSION_KEY);
    this.currentUser.set(null);
    this.router.navigate(['/']);
  }

  isAdmin(): boolean {
    return this.currentUser()?.rol === 'ADMIN';
  }

  private saveSession(user: UsuarioAuth): void {
    sessionStorage.setItem(this.SESSION_KEY, JSON.stringify(user));
  }

  private loadSession(): UsuarioAuth | null {
    const stored = sessionStorage.getItem(this.SESSION_KEY);
    return stored ? JSON.parse(stored) : null;
  }
}
