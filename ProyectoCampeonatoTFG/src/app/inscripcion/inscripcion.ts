import { Component, Input, Output, EventEmitter, signal, computed, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Campeonato } from '../interfaces/campeonato';
import { Categoria } from '../interfaces/categoria';
import { Inscripcion } from '../interfaces/inscripcion';

import { CategoriaService } from '../service/categoria-service';
import { InscripcionCompetidorService } from '../service/inscripcion-competidor-service';
import { CompetidorAuthService } from '../service/competidor-auth-service';
import { ValidadorService } from '../service/validador-service';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-inscripcion',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './inscripcion.html',
})
export class InscripcionComponent implements OnInit {
  private catSvc       = inject(CategoriaService);
  private inscSvc      = inject(InscripcionCompetidorService);
  private validadorSvc = inject(ValidadorService);
  readonly auth        = inject(CompetidorAuthService);

  @Input() campeonato!: Campeonato;
  @Output() cerrar     = new EventEmitter<void>();
  @Output() inscritoOk = new EventEmitter<void>();

  // ── Flujo ────────────────────────────────────────────────
  flujo = signal<'elegir' | 'login' | 'registro' | 'categorias' | 'confirmar'>('elegir');

  loading = signal(false);
  error   = signal<string | null>(null);

  // ── Login ─────────────────────────────────────────────────
  dniLogin      = signal('');
  passwordLogin = signal('');
  showPassLogin = signal(false);
  intentoLogin  = signal(false);

  dniLoginVal  = computed(() => this.validadorSvc.validarDNI(this.dniLogin()));
  dniLoginErr  = computed(() => this.intentoLogin() && !this.dniLoginVal().valido ? this.dniLoginVal().mensaje : null);
  passLoginErr = computed(() => this.intentoLogin() && !this.passwordLogin() ? 'Introduce tu contraseña' : null);

  // ── Registro ──────────────────────────────────────────────
  regNombre      = signal('');
  regApellidos   = signal('');
  regDni         = signal('');
  regEmail       = signal('');
  regPassword    = signal('');
  regClub        = signal('');
  regFedAuton    = signal('');
  regFechaNac    = signal('');
  regGenero      = signal<'M' | 'F'>('M');
  showPassReg    = signal(false);
  intentoReg     = signal(false);
  // Errores de validación del registro
  dniRegError    = signal<string | null>(null);
  emailRegError  = signal<string | null>(null);

  dniRegVal    = computed(() => this.validadorSvc.validarDNI(this.regDni()));
  passRegReglas = computed(() => this.validadorSvc.getReglasEstado(this.regPassword()));
  passRegValida = computed(() => this.validadorSvc.isPasswordValida(this.regPassword()));

  // ── Categorías ────────────────────────────────────────────
  categorias    = signal<Categoria[]>([]);
  seleccionadas = signal<Set<number>>(new Set());
  yaInscritas   = signal<Set<number>>(new Set());
  consentimiento = signal(false);

  /** Género del competidor logueado (para filtrar categorías) */
  private generoUsuario = computed(() => this.auth.currentCompetidor()?.genero ?? null);

  /** Categorías filtradas por el género del usuario logueado */
  private categoriasFiltradas = computed(() => {
    const genero = this.generoUsuario();
    if (!genero) return this.categorias();
    return this.categorias().filter(c => c.genero === genero);
  });

  /** Sección Kumite (filtrado por género y modalidad) */
  seccionKumite = computed(() =>
    this.categoriasFiltradas().filter(c => c.modalidad?.toLowerCase() === 'kumite')
  );

  /** Sección Kata (filtrado por género y modalidad) */
  seccionKata = computed(() =>
    this.categoriasFiltradas().filter(c => c.modalidad?.toLowerCase() === 'kata')
  );

  categoriasParaConfirmar = computed(() =>
    this.categorias().filter(c => this.seleccionadas().has(c.id_categoria))
  );

  readonly objectKeys = Object.keys;

  async ngOnInit() {
    if (this.auth.isLoggedIn()) {
      await this.cargarCategorias();
      this.flujo.set('categorias');
    }
  }

  // ── Paso 1: elegir ────────────────────────────────────────
  irALogin()    { this.error.set(null); this.flujo.set('login');    }
  irARegistro() { this.error.set(null); this.flujo.set('registro'); }

  // ── Paso 2a: login ────────────────────────────────────────
  async login() {
    this.intentoLogin.set(true);
    if (!this.dniLoginVal().valido || !this.passwordLogin()) return;

    this.loading.set(true);
    this.error.set(null);
    try {
      await this.auth.login(this.dniLogin(), this.passwordLogin());
      await this.cargarCategorias();
      this.flujo.set('categorias');
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al iniciar sesión');
    } finally {
      this.loading.set(false);
    }
  }

  // ── Paso 2b: registro ─────────────────────────────────────
  async registro() {
    this.intentoReg.set(true);
    this.dniRegError.set(null);
    this.emailRegError.set(null);

    if (
      !this.regNombre() || !this.regApellidos() || !this.dniRegVal().valido ||
      !this.regEmail() || !this.passRegValida() || !this.regClub() ||
      !this.regFedAuton() || !this.regFechaNac()
    ) return;

    // Validar formato email básico
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.regEmail())) {
      this.emailRegError.set('El formato del email no es válido');
      return;
    }

    this.loading.set(true);
    this.error.set(null);
    try {
      const body = {
        nombre:               this.regNombre(),
        apellidos:            this.regApellidos(),
        dni:                  this.regDni().toUpperCase(),
        email:                this.regEmail(),
        password:             this.regPassword(),
        club:                 this.regClub(),
        federacionAutonomica: this.regFedAuton(),
        fechaNacimiento:      this.regFechaNac(),
        genero:               this.regGenero(),
        rol:                  'COMPETIDOR',
      };

      const res = await fetch(`${environment.apiUrl}/api/competidores`, {
        method:  'POST',
        headers: { 'Content-Type': 'application/json' },
        body:    JSON.stringify(body),
      });

      if (res.status === 409) {
        // Intentar detectar qué campo está duplicado
        const errBody = await res.json().catch(() => ({}));
        const msg: string = errBody?.message ?? errBody?.error ?? '';
        if (msg.toLowerCase().includes('dni') || msg.toLowerCase().includes('nombre')) {
          this.dniRegError.set('Ya existe una cuenta con este DNI');
        } else if (msg.toLowerCase().includes('email')) {
          this.emailRegError.set('Ya existe una cuenta con este email');
        } else {
          this.error.set('Ya existe una cuenta con ese DNI o email');
        }
        return;
      }
      if (!res.ok) throw new Error('Error al crear la cuenta');

      // Login automático tras registro
      await this.auth.login(this.regDni(), this.regPassword());
      await this.cargarCategorias();
      this.flujo.set('categorias');
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al crear la cuenta');
    } finally {
      this.loading.set(false);
    }
  }

  // ── Paso 3: categorías ────────────────────────────────────
  private async cargarCategorias() {
    const competidor = this.auth.currentCompetidor();
    const [cats, misIns] = await Promise.all([
      this.catSvc.getCategoriasPorCampeonato(this.campeonato.id_campeonato),
      competidor
        ? this.inscSvc.getMisInscripciones(competidor.id).catch(() => [] as Inscripcion[])
        : Promise.resolve([] as Inscripcion[]),
    ]);
    this.categorias.set(cats);
    const yaInsc = misIns
      .filter(i => i.idCampeonato === this.campeonato.id_campeonato)
      .map(i => i.idCategoria);
    this.yaInscritas.set(new Set(yaInsc));
  }

  toggleCategoria(id: number) {
    if (this.yaInscritas().has(id)) return;
    const s = new Set(this.seleccionadas());
    s.has(id) ? s.delete(id) : s.add(id);
    this.seleccionadas.set(s);
  }

  estaSeleccionada(id: number) { return this.seleccionadas().has(id); }
  estaYaInscrito(id: number)   { return this.yaInscritas().has(id); }

  siguientePaso() {
    if (this.seleccionadas().size > 0) this.flujo.set('confirmar');
  }

  // ── Paso 4: confirmar ─────────────────────────────────────
  async confirmarInscripcion() {
    if (!this.consentimiento()) return;
    this.loading.set(true);
    this.error.set(null);
    try {
      const compId = this.auth.currentCompetidor()?.id;
      if (!compId) throw new Error('No se encontró tu sesión. Por favor, vuelve a iniciar sesión.');
      await Promise.all(
        Array.from(this.seleccionadas()).map(idCat =>
          this.inscSvc.inscribir(this.campeonato.id_campeonato, idCat, compId)
        )
      );
      this.inscritoOk.emit();
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al procesar la inscripción');
    } finally {
      this.loading.set(false);
    }
  }

  formatDate(d: string) { return new Date(d).toLocaleDateString('es-ES'); }
  cerrarModal()         { this.cerrar.emit(); }

  /** Etiqueta de género para el título de sección */
  get tituloGenero(): string {
    const g = this.generoUsuario();
    return g === 'M' ? 'Masculino' : g === 'F' ? 'Femenino' : '';
  }
}
