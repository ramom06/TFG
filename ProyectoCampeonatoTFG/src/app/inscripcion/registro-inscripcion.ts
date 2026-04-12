import {computed, EventEmitter, inject, Output, signal} from '@angular/core';
import {CompetidorAuthService} from '../service/competidor-auth-service';
import {ValidadorService} from '../service/validador-service';

export class RegistroInscripcionComponent {
  private auth = inject(CompetidorAuthService);
  private validadorSvc = inject(ValidadorService);

  @Output() registroOk = new EventEmitter<void>();

  nombre    = signal('');
  apellidos = signal('');
  dni       = signal('');
  email     = signal('');
  password  = signal('');
  club      = signal('');
  fedAuton  = signal('');
  fechaNac  = signal('');
  genero    = signal<'M' | 'F'>('M');
  showPass  = signal(false);
  intento   = signal(false);

  dniVal        = computed(() => this.validadorSvc.validarDNI(this.dni()));
  passRegReglas = computed(() => this.validadorSvc.getReglasEstado(this.password()));
  passRegValida = computed(() => this.validadorSvc.isPasswordValida(this.password()));

  async registrar() {
    this.intento.set(true);

    // Valida antes de enviar
    if (!this.dniVal().valido || !this.passRegValida() || !this.nombre()) {
      return;
    }

    try {
      this.registroOk.emit();
    } catch (error) {
      console.error(error);
    }
  }
}
