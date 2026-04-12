import {Component, EventEmitter, inject, Output, signal} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {CompetidorAuthService} from '../service/competidor-auth-service';

@Component({
  selector: 'app-login-inscripcion',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: ``
})
export class LoginInscripcion {
  private auth = inject(CompetidorAuthService);

  // Avisa al padre
  @Output() loginOk = new EventEmitter<void>();

  dni = signal('');
  pass = signal('');
  loading = signal(false);

  async login() {
    this.loading.set(true);
    try {
      await this.auth.login(this.dni(), this.pass());
      this.loginOk.emit(); // ¡Éxito!
    } catch (e) {
      alert('Error en el login');
    } finally { this.loading.set(false); }
  }
}
