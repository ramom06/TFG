import { Component, Input, Output, EventEmitter, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Categoria } from '../interfaces/categoria';

@Component({
  selector: 'app-selector-categorias',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './selector-categoria-inscripcion.html'
})
export class SelectorCategorias {
  @Input() categorias: Categoria[] = [];
  @Input() generoUsuario: 'M' | 'F' = 'M';
  @Input() edadUsuario: number = 0;
  @Input() yaInscritasIds: Set<number> = new Set();
  @Output() seleccionFinal = new EventEmitter<number[]>();

  seleccionadas = signal<Set<number>>(new Set());

  categoriasFiltradas = computed(() => {
    return this.categorias.filter(c =>
      c.genero === this.generoUsuario &&
      (!c.edadMinima || this.edadUsuario >= c.edadMinima) &&
      (!c.edadMaxima || this.edadUsuario <= c.edadMaxima)
    );
  });

  secciones = computed<Record<string, Categoria[]>>(() => {
    const cats = this.categoriasFiltradas();
    return {
      'Kata': cats.filter(c => c.modalidad?.toUpperCase() === 'KATA'),
      'Kumite': cats.filter(c => c.modalidad?.toUpperCase() === 'KUMITE')
    };
  });

  toggleCategoria(id: number) {
    const cat = this.categorias.find(c => c.id_categoria === id);
    if (!cat || this.yaInscritasIds.has(id)) return;

    const s = new Set(this.seleccionadas());
    if (s.has(id)) {
      s.delete(id);
    } else {
      // Regla: Solo una por modalidad. Si elijo Kumite A, se borra Kumite B.
      s.forEach(idSel => {
        const catSel = this.categorias.find(c => c.id_categoria === idSel);
        if (catSel?.modalidad === cat.modalidad) s.delete(idSel);
      });
      s.add(id);
    }
    this.seleccionadas.set(s);
    this.seleccionFinal.emit(Array.from(this.seleccionadas()));
  }

  estaSeleccionada(id: number) { return this.seleccionadas().has(id); }
}
