import { Component, Input, Output, EventEmitter, signal, computed, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoriaService } from '../service/categoria-service';
import { InscripcionCompetidorService } from '../service/inscripcion-competidor-service';
import { Categoria } from '../interfaces/categoria';

@Component({
  selector: 'app-selector-categorias',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './selector-categorias-inscripcion.html'
})
export class SelectorCategorias implements OnInit {
  private catSvc = inject(CategoriaService);
  private inscSvc = inject(InscripcionCompetidorService);

  @Input() idCampeonato!: number;
  @Input() idCompetidor!: number;
  @Input() generoUsuario: 'M' | 'F' = 'M';
  @Input() edadUsuario: number = 0;

  @Output() seleccionFinal = new EventEmitter<number[]>();

  // Estado interno
  todasLasCategorias = signal<Categoria[]>([]);
  yaInscritasIds = signal<Set<number>>(new Set());
  seleccionadas = signal<Set<number>>(new Set());
  loading = signal(true);

  async ngOnInit() {
    try {
      // El hijo hace las peticiones
      const [cats, misIns] = await Promise.all([
        this.catSvc.getCategoriasPorCampeonato(this.idCampeonato),
        this.inscSvc.getMisInscripciones(this.idCompetidor)
      ]);

      this.todasLasCategorias.set(cats);

      const inscritas = misIns
        .filter(i => i.idCampeonato === this.idCampeonato)
        .map(i => i.idCategoria);
      this.yaInscritasIds.set(new Set(inscritas));
    } catch (e) {
      console.error("Error cargando categorías en el hijo", e);
    } finally {
      this.loading.set(false);
    }
  }

  // El filtrado se mantiene aquí (Edad + Género)
  categoriasFiltradas = computed(() => {
    return this.todasLasCategorias().filter(c =>
      c.genero === this.generoUsuario &&
      (!c.edadMinima || this.edadUsuario >= c.edadMinima) &&
      (!c.edadMaxima || this.edadUsuario <= c.edadMaxima)
    );
  });

  secciones = computed(() => ({
    'Kata': this.categoriasFiltradas().filter(c => c.modalidad?.toUpperCase() === 'KATA'),
    'Kumite': this.categoriasFiltradas().filter(c => c.modalidad?.toUpperCase() === 'KUMITE')
  }));

  toggleCategoria(id: number) {
    if (this.yaInscritasIds().has(id)) return;
    const s = new Set(this.seleccionadas());

    if (s.has(id)) {
      s.delete(id);
    } else {
      // Regla de una por modalidad
      const cat = this.todasLasCategorias().find(c => c.id_categoria === id);
      s.forEach(idSel => {
        const catSel = this.todasLasCategorias().find(c => c.id_categoria === idSel);
        if (catSel?.modalidad === cat?.modalidad) s.delete(idSel);
      });
      s.add(id);
    }
    this.seleccionadas.set(s);
    this.seleccionFinal.emit(Array.from(this.seleccionadas()));
  }
}
