import { Component, Input, Output, EventEmitter, signal, computed, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Campeonato } from '../interfaces/campeonato';
import { CompetidorAuthService } from '../service/competidor-auth-service';
import { CategoriaService } from '../service/categoria-service';
import { InscripcionCompetidorService } from '../service/inscripcion-competidor-service';
import { SelectorCategorias } from './selector-categorias-inscripcion';

@Component({
  selector: 'app-inscripcion',
  standalone: true,
  imports: [CommonModule, SelectorCategorias],
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
  seleccionadas = signal<number[]>([]);

  edad = computed(() => {
    const user = this.auth.currentCompetidor();
    if (!user?.fechaNacimiento) return 0;
    const nac = new Date(user.fechaNacimiento);
    const hoy = new Date();
    let edad = hoy.getFullYear() - nac.getFullYear();
    if (hoy.getMonth() < nac.getMonth() || (hoy.getMonth() === nac.getMonth() && hoy.getDate() < nac.getDate())) edad--;
    return edad;
  });

  async ngOnInit() {
    if (this.auth.isLoggedIn()) {
      await this.cargarDatos();
      this.flujo.set('categorias');
    }
  }

  async cargarDatos() {
    try {
      const [cats, misIns] = await Promise.all([
        this.catSvc.getCategoriasPorCampeonato(this.campeonato.id_campeonato),
        this.inscSvc.getMisInscripciones(this.auth.currentCompetidor()!.id)
      ]);
      this.categorias.set(cats);
      const ids = misIns.filter(i => i.idCampeonato === this.campeonato.id_campeonato).map(i => i.idCategoria);
      this.yaInscritasIds.set(new Set(ids));
    } catch (e) { this.error.set('Error al cargar datos'); }
  }

  actualizarSeleccion(ids: number[]) { this.seleccionadas.set(ids); }

  async confirmarInscripcion() {
    this.loading.set(true);
    try {
      const compId = this.auth.currentCompetidor()!.id;
      await Promise.all(this.seleccionadas().map(idCat =>
        this.inscSvc.inscribir(this.campeonato.id_campeonato, idCat, compId)
      ));
      this.inscritoOk.emit();
    } catch (e: any) { this.error.set(e.message); }
    finally { this.loading.set(false); }
  }
}
