import { Component, Output, EventEmitter, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { environment } from '../../environments/environment';
import { CompetidorAuthService } from '../service/competidor-auth-service';
import { Rol } from '../interfaces/usuario';

@Component({
  selector: 'app-registro-inscripcion',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './registro-inscripcion.html'
})
export class RegistroInscripcion {
  // Inyectamos el servicio para poder hacer el login automático después de registrar
  private auth = inject(CompetidorAuthService);

  @Output() registroOk = new EventEmitter<void>();

  nombre = signal('');
  apellidos = signal('');
  dni = signal('');
  email = signal('');
  password = signal('');
  club = signal('');
  federacion = signal('');
  fechaNac = signal('');
  genero = signal<'M' | 'F'>('M');

  loading = signal(false);
  error = signal<string | null>(null);

  async registrar() {
    if (!this.nombre() || !this.dni() || !this.password()) {
      this.error.set('Por favor, rellena los campos obligatorios');
      return;
    }

    this.loading.set(true);
    this.error.set(null);

    try {
      const body = {
        nombre: this.nombre(),
        apellidos: this.apellidos(),
        dni: this.dni().toUpperCase(),
        email: this.email(),
        password: this.password(),
        club: this.club(),
        federacionAutonomica: this.federacion(),
        fechaNacimiento: this.fechaNac(),
        genero: this.genero(),
        rol: Rol.COMPETIDOR
      };

      // 1. Creamos la cuenta en el backend
      const res = await fetch(`${environment.apiUrl}/api/competidores`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
      });

      if (!res.ok) throw new Error('Error al crear la cuenta. El DNI o Email podrían estar ya en uso.');

      // 2. LOGUEO AUTOMÁTICO:
      // Aquí es donde usamos el servicio. Llamamos a login() con los datos que
      // el usuario acaba de escribir. Así el servicio guarda la sesión en el Storage.
      await this.auth.login(this.dni(), this.password());

      // 3. Avisamos al padre (InscripcionComponent) que ya puede pasar a las categorías
      this.registroOk.emit();

    } catch (e: any) {
      this.error.set(e.message);
    } finally {
      this.loading.set(false);
    }
  }
}
