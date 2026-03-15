import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AutenticacionService } from '../service/autenticacion-service';

@Component({
  selector: 'app-admin-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './admin-login.html',
  styleUrl: './admin-login.css',
})
export class AdminLoginComponent {

  dni      = signal('');
  password = signal('');
  loading  = signal(false);
  error    = signal<string | null>(null);
  showPass = signal(false);

  constructor(private auth: AutenticacionService, private router: Router) {
    if (this.auth.isAdmin()) {
      this.router.navigate(['/admin/campeonatos']);
    }
  }

  async submit(): Promise<void> {
    if (!this.dni() || !this.password()) {
      this.error.set('Introduce el DNI y la contraseña');
      return;
    }

    this.loading.set(true);
    this.error.set(null);

    try {
      await this.auth.login(this.dni(), this.password());
      this.router.navigate(['/admin/campeonatos']);
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al iniciar sesión');
    } finally {
      this.loading.set(false);
    }
  }
}
