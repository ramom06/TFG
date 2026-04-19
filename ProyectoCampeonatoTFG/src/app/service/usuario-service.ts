import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Usuario } from '../interfaces/usuario';

@Injectable({ providedIn: 'root' })
export class UsuarioService {

  private readonly apiUrl = `${environment.apiUrl}/api/usuarios`;

  async getAll(): Promise<Usuario[]> {
    const res = await fetch(this.apiUrl);
    if (!res.ok) throw new Error('Error al cargar usuarios');
    return res.json();
  }

  async getByRol(rol: string): Promise<Usuario[]> {
    const res = await fetch(`${this.apiUrl}/rol/${rol}`);
    if (!res.ok) throw new Error('Error al cargar usuarios por rol');
    return res.json();
  }

  async create(usuario: Partial<Usuario>): Promise<Usuario> {
    const res = await fetch(this.apiUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(usuario),
    });
    if (!res.ok) {
      const err = await res.json().catch(() => ({}));
      throw new Error(err.message ?? 'Error al crear el usuario');
    }
    return res.json();
  }

  async update(id: number, usuario: Partial<Usuario>): Promise<Usuario> {
    const res = await fetch(`${this.apiUrl}/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(usuario),
    });
    if (!res.ok) {
      const err = await res.json().catch(() => ({}));
      throw new Error(err.message ?? 'Error al actualizar el usuario');
    }
    return res.json();
  }

  async delete(id: number): Promise<void> {
    const res = await fetch(`${this.apiUrl}/${id}`, { method: 'DELETE' });
    if (!res.ok) throw new Error('Error al eliminar el usuario');
  }
}
