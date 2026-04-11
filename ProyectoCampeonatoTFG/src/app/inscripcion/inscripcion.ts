import {
  Component, Input, Output, EventEmitter,
  signal, computed, OnInit, inject,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule }  from '@angular/forms';

import { Campeonato }    from '../interfaces/campeonato';
import { Categoria }     from '../interfaces/categoria';
import { Inscripcion }   from '../interfaces/inscripcion';

import { CategoriaService }             from '../service/categoria-service';
import { InscripcionCompetidorService } from '../service/inscripcion-competidor-service';
import { CompetidorAuthService }        from '../service/competidor-auth-service';

interface Paso { id: number; label: string }

@Component({
  selector: 'app-inscripcion',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './inscripcion.html',
})
export class InscripcionComponent implements OnInit {

  @Input()  campeonato!: Campeonato;
  @Output() cerrar     = new EventEmitter<void>();
  @Output() inscritoOk = new EventEmitter<void>();

  // ── Servicios ──────────────────────────────────────────────────────────────
  private catSvc  = inject(CategoriaService);
  private inscSvc = inject(InscripcionCompetidorService);
  readonly auth   = inject(CompetidorAuthService);

  // ── Estado UI ──────────────────────────────────────────────────────────────
  paso    = signal<number>(1);
  loading = signal(false);
  error   = signal<string | null>(null);

  // ── Login ──────────────────────────────────────────────────────────────────
  dni      = signal('');
  password = signal('');
  showPass = signal(false);

  // ── Categorías ─────────────────────────────────────────────────────────────
  categorias    = signal<Categoria[]>([]);
  seleccionadas = signal<Set<number>>(new Set());
  yaInscritas   = signal<Set<number>>(new Set());

  // ── RGPD ───────────────────────────────────────────────────────────────────
  consentimiento = signal(false);

  // ── Computed ───────────────────────────────────────────────────────────────
  masculino = computed(() =>
    this.agrupar(this.categorias().filter(c => c.genero === 'M'))
  );
  femenino = computed(() =>
    this.agrupar(this.categorias().filter(c => c.genero === 'F'))
  );
  categoriasParaConfirmar = computed(() =>
    this.categorias().filter(c => this.seleccionadas().has(c.id_categoria))
  );

  readonly pasos: Paso[] = [
    { id: 1, label: 'Identificación' },
    { id: 2, label: 'Categorías'     },
    { id: 3, label: 'Confirmación'   },
  ];

  // ── Lifecycle ──────────────────────────────────────────────────────────────
  async ngOnInit() {
    if (this.auth.isLoggedIn()) {
      await this.cargarDatos();
      this.paso.set(2);
    }
  }

  // ── Login ──────────────────────────────────────────────────────────────────
  async login() {
    if (!this.dni() || !this.password()) {
      this.error.set('Introduce tu DNI y contraseña');
      return;
    }
    this.loading.set(true);
    this.error.set(null);
    try {
      await this.auth.login(this.dni(), this.password());
      await this.cargarDatos();
      this.paso.set(2);
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al iniciar sesión');
    } finally {
      this.loading.set(false);
    }
  }

  // ── Cargar datos del competidor ────────────────────────────────────────────
  private async cargarDatos() {
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
  }

  // ── Selección ──────────────────────────────────────────────────────────────
  toggleCategoria(id: number) {
    if (this.yaInscritas().has(id)) return;
    const s = new Set(this.seleccionadas());
    s.has(id) ? s.delete(id) : s.add(id);
    this.seleccionadas.set(s);
  }

  estaSeleccionada(id: number) { return this.seleccionadas().has(id); }
  estaYaInscrito(id: number)   { return this.yaInscritas().has(id);   }

  siguientePaso() {
    if (this.seleccionadas().size === 0) {
      this.error.set('Debes seleccionar al menos una categoría');
      return;
    }
    this.error.set(null);
    this.paso.set(3);
  }

  volverAPaso2() {
    this.consentimiento.set(false);
    this.error.set(null);
    this.paso.set(2);
  }

  // ── Confirmar ──────────────────────────────────────────────────────────────
  async confirmarInscripcion() {
    if (!this.consentimiento()) {
      this.error.set('Debes aceptar el tratamiento de datos para continuar');
      return;
    }
    const comp = this.auth.currentCompetidor();
    if (!comp) { this.error.set('No hay sesión activa'); return; }

    this.loading.set(true);
    this.error.set(null);
    try {
      await Promise.all(
        Array.from(this.seleccionadas()).map(idCat =>
          this.inscSvc.inscribir(this.campeonato.id_campeonato, idCat, comp.id)
        )
      );
      this.inscritoOk.emit();
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al inscribirse');
    } finally {
      this.loading.set(false);
    }
  }

  // ── Helpers ────────────────────────────────────────────────────────────────
  private agrupar(cats: Categoria[]): Record<string, Categoria[]> {
    return cats.reduce((acc, cat) => {
      const key = cat.modalidad.charAt(0).toUpperCase() + cat.modalidad.slice(1);
      (acc[key] ??= []).push(cat);
      return acc;
    }, {} as Record<string, Categoria[]>);
  }

  readonly objectKeys = Object.keys;

  formatDate(d: string) {
    return new Date(d).toLocaleDateString('es-ES', { day: '2-digit', month: 'short', year: 'numeric' });
  }

  cerrarModal() { this.cerrar.emit(); }
}
