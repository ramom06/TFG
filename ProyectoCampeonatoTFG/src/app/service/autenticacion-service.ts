import { Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';

export interface UsuarioAuth {
  id: number;
  nombre: string;
  email: string;
  rol: string;
}

@Injectable({ providedIn: 'root' })
export class AutenticacionService {

  private readonly apiUrl = 'http://localhost:8080/api/usuarios';
  private readonly SESSION_KEY = 'admin_session';

  currentUser = signal<UsuarioAuth | null>(this.loadSession());

  constructor(private router: Router) {}

  /** Llama a POST /api/usuarios/login con DNI + contraseña */
  async login(dni: string, password: string): Promise<void> {
    const response = await fetch(`${this.apiUrl}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ dni, password }),
    });

    if (response.status === 401) throw new Error('Credenciales incorrectas');
    if (response.status === 403) throw new Error('No tienes permisos de administrador');
    if (!response.ok)            throw new Error('No se puede conectar con el servidor');

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
