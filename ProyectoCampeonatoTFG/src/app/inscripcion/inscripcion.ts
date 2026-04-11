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

interface Paso { id: number; label: string }

@Component({
  selector: 'app-inscripcion',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './inscripcion.html',
})
export class InscripcionComponent implements OnInit {
  // --- Inyecciones ---
  private catSvc       = inject(CategoriaService);
  private inscSvc      = inject(InscripcionCompetidorService);
  private validadorSvc = inject(ValidadorService); // <--- Inyectamos
  readonly auth        = inject(CompetidorAuthService);

  @Input() campeonato!: Campeonato;
  @Output() cerrar = new EventEmitter<void>();
  @Output() inscritoOk = new EventEmitter<void>();

  // --- Estado UI ---
  paso    = signal<number>(1);
  loading = signal(false);
  error   = signal<string | null>(null);

  // --- Campos y Validaciones con Signals ---
  dni          = signal('');
  password     = signal('');
  showPass     = signal(false);
  intentoLogin = signal(false);
  mostrarReglas = signal(false);

  // Delegamos la validación al servicio mediante computed
  dniValidacion = computed(() => this.validadorSvc.validarDNI(this.dni()));
  dniValido     = computed(() => this.dniValidacion().valido);
  dniError      = computed(() => this.intentoLogin() && !this.dniValido() ? this.dniValidacion().mensaje : null);

  reglasEstado   = computed(() => this.validadorSvc.getReglasEstado(this.password()));
  passwordValida = computed(() => this.validadorSvc.isPasswordValida(this.password()));
  passwordError  = computed(() => this.intentoLogin() && !this.passwordValida() ? 'La contraseña no es segura' : null);

  // --- Datos de categorías ---
  categorias    = signal<Categoria[]>([]);
  seleccionadas = signal<Set<number>>(new Set());
  yaInscritas   = signal<Set<number>>(new Set());
  consentimiento = signal(false);

  // --- Computed para la vista ---
  masculino = computed(() => this.agrupar(this.categorias().filter(c => c.genero === 'M')));
  femenino  = computed(() => this.agrupar(this.categorias().filter(c => c.genero === 'F')));
  categoriasParaConfirmar = computed(() => this.categorias().filter(c => this.seleccionadas().has(c.id_categoria)));

  readonly pasos: Paso[] = [
    { id: 1, label: 'Identificación' },
    { id: 2, label: 'Categorías' },
    { id: 3, label: 'Confirmación' },
  ];

  async ngOnInit() {
    if (this.auth.isLoggedIn()) {
      await this.cargarDatos();
      this.paso.set(2);
    }
  }

  async login() {
    this.intentoLogin.set(true);
    if (!this.dniValido() || !this.passwordValida()) return;

    this.loading.set(true);
    this.error.set(null);
    try {
      await this.auth.login(this.dni(), this.password());
      await this.cargarDatos();
      this.paso.set(2);
    } catch (e: any) {
      this.error.set(e.message?.includes('fetch') ? 'Error de conexión con el servidor' : 'DNI o contraseña incorrectos');
    } finally {
      this.loading.set(false);
    }
  }

  // ... (cargarDatos, toggleCategoria, confirmarInscripcion y agrupar se mantienen igual)
  // Nota: asegúrate de que agrupar use cat.modalidad para las llaves.

  private async cargarDatos() {
    try {
      const [cats, misIns] = await Promise.all([
        this.catSvc.getCategoriasPorCampeonato(this.campeonato.id_campeonato),
        this.auth.currentCompetidor()
          ? this.inscSvc.getMisInscripciones(this.auth.currentCompetidor()!.id).catch(() => [] as Inscripcion[])
          : Promise.resolve([] as Inscripcion[]),
      ]);
      this.categorias.set(cats);
      const estecamp = misIns
        .filter(i => i.idCampeonato === this.campeonato.id_campeonato)
        .map(i => i.idCategoria);
      this.yaInscritas.set(new Set(estecamp));
    } catch (e) {
      this.error.set('Error al cargar datos del campeonato');
    }
  }

  toggleCategoria(id: number) {
    if (this.yaInscritas().has(id)) return;
    const s = new Set(this.seleccionadas());
    s.has(id) ? s.delete(id) : s.add(id);
    this.seleccionadas.set(s);
  }

  estaSeleccionada(id: number) { return this.seleccionadas().has(id); }
  estaYaInscrito(id: number) { return this.yaInscritas().has(id); }

  siguientePaso() { if (this.seleccionadas().size > 0) this.paso.set(3); }
  volverAPaso2()  { this.paso.set(2); }

  async confirmarInscripcion() {
    if (!this.consentimiento()) return;
    this.loading.set(true);
    try {
      const compId = this.auth.currentCompetidor()!.id;
      await Promise.all(
        Array.from(this.seleccionadas()).map(idCat =>
          this.inscSvc.inscribir(this.campeonato.id_campeonato, idCat, compId)
        )
      );
      this.inscritoOk.emit();
    } catch (e) {
      this.error.set('Error al procesar la inscripción');
    } finally {
      this.loading.set(false);
    }
  }

  private agrupar(cats: Categoria[]): Record<string, Categoria[]> {
    return cats.reduce((acc, cat) => {
      const key = cat.modalidad || 'General';
      (acc[key] ??= []).push(cat);
      return acc;
    }, {} as Record<string, Categoria[]>);
  }

  readonly objectKeys = Object.keys;
  formatDate(d: string) { return new Date(d).toLocaleDateString(); }
  cerrarModal() { this.cerrar.emit(); }
}
