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
  private auth = inject(CompetidorAuthService);

  @Output() registroOk = new EventEmitter<void>();

  // Form Fields
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

  // Ahora es un objeto para mostrar errores por cada input
  errores = signal<Record<string, string>>({});
  errorGlobal = signal<string | null>(null);

  async registrar() {
    // 1. Validaciones previas al envío
    const newErrors: Record<string, string> = {};

    if (!this.nombre()) newErrors['nombre'] = 'El nombre es obligatorio';
    if (!this.apellidos()) newErrors['apellidos'] = 'Los apellidos son obligatorios';
    if (!this.dni() || this.dni().length < 9) newErrors['dni'] = 'DNI incompleto o inválido';
    if (!this.email().includes('@')) newErrors['email'] = 'Email no válido';
    if (this.password().length < 6) newErrors['password'] = 'Mínimo 6 caracteres';
    if (!this.fechaNac()) newErrors['fechaNac'] = 'Fecha de nacimiento obligatoria';

    this.errores.set(newErrors);

    // Si hay errores, no seguimos
    if (Object.keys(newErrors).length > 0) return;

    this.loading.set(true);
    this.errorGlobal.set(null);

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

      const res = await fetch(`${environment.apiUrl}/api/competidores`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
      });

      if (!res.ok) {
        throw new Error('Error al crear la cuenta. El DNI o Email podrían estar ya en uso.');
      }

      // Login automático
      await this.auth.login(this.dni(), this.password());

      this.registroOk.emit();

    } catch (e: any) {
      this.errorGlobal.set(e.message);
    } finally {
      this.loading.set(false);
    }
  }
}
