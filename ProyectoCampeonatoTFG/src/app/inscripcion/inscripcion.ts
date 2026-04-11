import {
  Component, Input, Output, EventEmitter,
  signal, computed, OnInit, inject,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Campeonato } from '../interfaces/campeonato';
import { Categoria } from '../interfaces/categoria';
import { Inscripcion } from '../interfaces/inscripcion';

import { CategoriaService } from '../service/categoria-service';
import { InscripcionCompetidorService } from '../service/inscripcion-competidor-service';
import { CompetidorAuthService } from '../service/competidor-auth-service';

interface Paso { id: number; label: string }

// --- Utilidades de Validación ---
const LETRAS_DNI = 'TRWAGMYFPDXBNJZSQVHLCKE';

function validarDNI(dni: string): { valido: boolean; letraEsperada?: string; mensaje?: string } {
  const limpio = dni.trim().toUpperCase();
  if (limpio.length !== 9) {
    return { valido: false, mensaje: 'El DNI debe tener 9 caracteres (8 números + letra)' };
  }
  const numeros = limpio.slice(0, 8);
  const letra = limpio.slice(8);
  if (!/^\d{8}$/.test(numeros)) {
    return { valido: false, mensaje: 'Los primeros 8 caracteres deben ser números' };
  }
  const letraEsperada = LETRAS_DNI[parseInt(numeros, 10) % 23];
  if (letra !== letraEsperada) {
    return { valido: false, letraEsperada, mensaje: `Letra incorrecta. Para ${numeros} corresponde la "${letraEsperada}"` };
  }
  return { valido: true, letraEsperada };
}

interface ReglaPassword { id: string; texto: string; cumple: (p: string) => boolean; }

const REGLAS_PASSWORD: ReglaPassword[] = [
  { id: 'len',   texto: 'Mínimo 8 caracteres',           cumple: p => p.length >= 8 },
  { id: 'upper', texto: 'Al menos una letra mayúscula',  cumple: p => /[A-Z]/.test(p) },
  { id: 'lower', texto: 'Al menos una letra minúscula',  cumple: p => /[a-z]/.test(p) },
  { id: 'digit', texto: 'Al menos un número',            cumple: p => /\d/.test(p) },
];

@Component({
  selector: 'app-inscripcion', // Mantenemos el selector original para que no falle el HTML padre
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './inscripcion.html', // Asegúrate de que tu HTML se llame así
})
export class InscripcionComponent implements OnInit {

  @Input() campeonato!: Campeonato;
  @Output() cerrar = new EventEmitter<void>();
  @Output() inscritoOk = new EventEmitter<void>();

  private catSvc = inject(CategoriaService);
  private inscSvc = inject(InscripcionCompetidorService);
  readonly auth = inject(CompetidorAuthService);

  // -- Estado --
  paso = signal<number>(1);
  loading = signal(false);
  error = signal<string | null>(null);

  // -- Login y Validaciones --
  dni = signal('');
  password = signal('');
  showPass = signal(false);
  intentoLogin = signal(false);

  dniValidacion = computed(() => validarDNI(this.dni()));
  dniValido = computed(() => this.dniValidacion().valido);

  readonly reglas = REGLAS_PASSWORD;
  reglasEstado = computed(() => this.reglas.map(r => ({ ...r, ok: r.cumple(this.password()) })));
  passwordValida = computed(() => this.reglasEstado().every(r => r.ok));
  mostrarReglas = signal(false);

  // -- Datos --
  categorias = signal<Categoria[]>([]);
  seleccionadas = signal<Set<number>>(new Set());
  yaInscritas = signal<Set<number>>(new Set());
  consentimiento = signal(false);

  masculino = computed(() => this.agrupar(this.categorias().filter(c => c.genero === 'M')));
  femenino = computed(() => this.agrupar(this.categorias().filter(c => c.genero === 'F')));
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
    if (!this.dniValido() || !this.passwordValida()) {
      this.error.set('Revisa los datos del formulario');
      return;
    }

    this.loading.set(true);
    this.error.set(null);
    try {
      await this.auth.login(this.dni(), this.password());
      await this.cargarDatos();
      this.paso.set(2);
    } catch (e: any) {
      if (e instanceof TypeError && e.message.includes('fetch')) {
        this.error.set('Servidor no disponible (Backend apagado)');
      } else {
        this.error.set(e.message ?? 'Credenciales incorrectas');
      }
    } finally {
      this.loading.set(false);
    }
  }

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
      this.error.set('Error al cargar categorías');
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

  siguientePaso() {
    if (this.seleccionadas().size === 0) {
      this.error.set('Selecciona al menos una categoría');
      return;
    }
    this.paso.set(3);
  }

  volverAPaso2() { this.paso.set(2); }

  async confirmarInscripcion() {
    if (!this.consentimiento()) {
      this.error.set('Debes aceptar el tratamiento de datos');
      return;
    }
    const comp = this.auth.currentCompetidor();
    if (!comp) return;

    this.loading.set(true);
    try {
      await Promise.all(
        Array.from(this.seleccionadas()).map(idCat =>
          this.inscSvc.inscribir(this.campeonato.id_campeonato, idCat, comp.id)
        )
      );
      this.inscritoOk.emit();
    } catch (e: any) {
      this.error.set('Error en el proceso de inscripción');
    } finally {
      this.loading.set(false);
    }
  }

  private agrupar(cats: Categoria[]): Record<string, Categoria[]> {
    return cats.reduce((acc, cat) => {
      const key = cat.modalidad.charAt(0).toUpperCase() + cat.modalidad.slice(1);
      (acc[key] ??= []).push(cat);
      return acc;
    }, {} as Record<string, Categoria[]>);
  }

  readonly objectKeys = Object.keys;
  cerrarModal() { this.cerrar.emit(); }
}
