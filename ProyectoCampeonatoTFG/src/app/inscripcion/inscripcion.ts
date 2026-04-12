import { Component, Input, Output, EventEmitter, signal, computed, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Campeonato } from '../interfaces/campeonato';
import { CompetidorAuthService } from '../service/competidor-auth-service';
import { CategoriaService } from '../service/categoria-service';
import { InscripcionCompetidorService } from '../service/inscripcion-competidor-service';
import { SelectorCategorias } from './selector-categorias-inscripcion';
import { RegistroInscripcion } from './registro-inscripcion';
import { LoginInscripcion } from './login-inscripcion';

@Component({
  selector: 'app-inscripcion',
  standalone: true,
  imports: [
    CommonModule,
    SelectorCategorias,
    RegistroInscripcion,
    LoginInscripcion
  ],
  templateUrl: './inscripcion.html'
})
export class InscripcionComponent implements OnInit {
  readonly auth = inject(CompetidorAuthService);
  private catSvc = inject(CategoriaService);
  private inscSvc = inject(InscripcionCompetidorService);

  @Input() campeonato!: Campeonato;
  @Output() cerrar = new EventEmitter<void>();
  @Output() inscritoOk = new EventEmitter<void>();

  flujo = signal<'elegir' | 'login' | 'registro' | 'categorias' | 'confirmar'>('elegir');
  loading = signal(false);
  error = signal<string | null>(null);

  categorias = signal<any[]>([]);
  yaInscritasIds = signal<Set<number>>(new Set());
  seleccionadasIds = signal<number[]>([]);

  edad = computed(() => {
    const user = this.auth.currentCompetidor();
    if (!user?.fechaNacimiento) return 0;
    const nac = new Date(user.fechaNacimiento);
    const hoy = new Date();
    let edad = hoy.getFullYear() - nac.getFullYear();
    const m = hoy.getMonth() - nac.getMonth();
    if (m < 0 || (m === 0 && hoy.getDate() < nac.getDate())) edad--;
    return edad;
  });

  async ngOnInit() {
    if (this.auth.isLoggedIn()) {
      await this.prepararInscripcion();
      this.flujo.set('categorias');
    }
  }

  async prepararInscripcion() {
    const competidor = this.auth.currentCompetidor();

    // Validamos que el competidor y su ID existan antes de llamar al servidor
    if (!competidor || !competidor.id) {
      console.warn('Esperando a que el competidor esté disponible...');
      return;
    }

    this.loading.set(true);
    try {
      const [cats, misIns] = await Promise.all([
        this.catSvc.getCategoriasPorCampeonato(this.campeonato.id_campeonato),
        this.inscSvc.getMisInscripciones(competidor.id) // Aquí ya no será undefined
      ]);

      this.categorias.set(cats);
      const ids = misIns
        .filter(i => i.idCampeonato === this.campeonato.id_campeonato)
        .map(i => i.idCategoria);
      this.yaInscritasIds.set(new Set(ids));
      this.error.set(null); // Limpiamos errores previos si los hubiera
    } catch (e) {
      this.error.set('Error al conectar con el servidor');
      console.error(e);
    } finally {
      this.loading.set(false);
    }
  }

  onAuthSuccess() {
    this.prepararInscripcion();
    this.flujo.set('categorias');
  }

  async confirmarFinal() {
    this.loading.set(true);
    try {
      const compId = this.auth.currentCompetidor()!.id;
      await Promise.all(this.seleccionadasIds().map(idCat =>
        this.inscSvc.inscribir(this.campeonato.id_campeonato, idCat, compId)
      ));
      this.inscritoOk.emit();
    } catch (e: any) {
      this.error.set(e.message);
    } finally {
      this.loading.set(false);
    }
  }
}
