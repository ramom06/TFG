import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { CampeonatoService } from '../service/campeonato-service';
import { CategoriaService } from '../service/categoria-service';
import {AutenticacionService} from '../service/autenticacion-service';
import { Campeonato, Estado, Nivel } from '../interfaces/campeonato';
import { Categoria } from '../interfaces/categoria';

/** Grupo de categorías para el selector del formulario */
export interface GrupoCategoria {
  grupo: string;  // ej: "Junior", "Senior"
  masculino: Categoria[];
  femenino: Categoria[];
}

@Component({
  selector: 'app-campeonato-admin',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterLink],
  templateUrl: './campeonato-admin.html',
  styleUrl: './campeonato-admin.css',
})
export class CampeonatoAdminComponent implements OnInit {

  campeonatos  = signal<Campeonato[]>([]);
  loading      = signal(true);
  saving       = signal(false);
  error        = signal<string | null>(null);
  successMsg   = signal<string | null>(null);

  // Todas las categorías disponibles (para el selector)
  todasCategorias = signal<Categoria[]>([]);
  // IDs de categorías seleccionadas para el campeonato que se está editando/creando
  categoriasSeleccionadas = signal<Set<number>>(new Set());

  // Grupos de categorías para mostrar en el selector
  gruposCategorias = computed<GrupoCategoria[]>(() =>
    this.agruparCategorias(this.todasCategorias())
  );

  // Modal crear/editar
  modalAbierto     = signal(false);
  modoEdicion      = signal(false);
  campeonatoEditId = signal<number | null>(null);

  // Modal confirmación borrado
  modalBorrarAbierto = signal(false);
  campeonatoABorrar  = signal<Campeonato | null>(null);

  searchText = signal('');
  filteredCampeonatos = computed(() => {
    const txt = this.searchText().toLowerCase();
    if (!txt) return this.campeonatos();
    return this.campeonatos().filter(c =>
      c.nombre.toLowerCase().includes(txt) ||
      c.ubicacion.toLowerCase().includes(txt)
    );
  });

  form!: FormGroup;

  constructor(
    private svc: CampeonatoService,
    private catSvc: CategoriaService,
    private auth: AutenticacionService,
    private router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.buildForm();
    this.cargarCampeonatos();
    this.cargarTodasCategorias();
  }

  private buildForm(): void {
    this.form = this.fb.group({
      nombre:     ['', [Validators.required, Validators.minLength(3)]],
      fechaInicio:['', Validators.required],
      fechaFin:   ['', Validators.required],
      ubicacion:  ['', Validators.required],
      estado:     ['futuro', Validators.required],
      nivel:      ['nacional', Validators.required],
      descripcion:[''],
      urlPortada: ['', Validators.required],
    });
  }

  async cargarCampeonatos(): Promise<void> {
    this.loading.set(true);
    this.error.set(null);
    try {
      const data = await this.svc.getAllCampeonatos();
      this.campeonatos.set(data.sort((a, b) =>
        new Date(b.fechaInicio).getTime() - new Date(a.fechaInicio).getTime()
      ));
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al cargar campeonatos');
    } finally {
      this.loading.set(false);
    }
  }

  async cargarTodasCategorias(): Promise<void> {
    try {
      // Usamos el endpoint GET /api/categorias (ya existe)
      const response = await fetch('http://localhost:8080/api/categorias');
      if (!response.ok) throw new Error();
      const data: Categoria[] = await response.json();
      this.todasCategorias.set(data);
    } catch {
      // No bloquea si falla
    }
  }

  // ── Agrupación de categorías ─────────────────────────────
  private agruparCategorias(cats: Categoria[]): GrupoCategoria[] {
    const mapGrupo = (cat: Categoria): string => {
      const min = cat.edadMinima ?? 0;
      const max = cat.edadMaxima ?? 99;
      if (max <= 7)  return 'Benjamín';
      if (max <= 9)  return 'Alevín';
      if (max <= 11) return 'Infantil';
      if (max <= 13) return 'Juvenil';
      if (max <= 15) return 'Cadete';
      if (max <= 17) return 'Junior';
      if (max <= 20) return 'Sub-21';
      if (min >= 35) return 'Master';
      return 'Senior';
    };

    const mapa = new Map<string, GrupoCategoria>();
    const orden = ['Benjamín','Alevín','Infantil','Juvenil','Cadete','Junior','Sub-21','Senior','Master'];

    for (const cat of cats) {
      const grupo = mapGrupo(cat);
      if (!mapa.has(grupo)) mapa.set(grupo, { grupo, masculino: [], femenino: [] });
      const g = mapa.get(grupo)!;
      if (cat.genero === 'M') g.masculino.push(cat);
      else g.femenino.push(cat);
    }

    return orden.filter(o => mapa.has(o)).map(o => mapa.get(o)!);
  }

  // Devuelve el grupo seleccionado para un grupo (true si todos están, false si ninguno, null si parcial)
  grupoTodosSeleccionados(grupo: GrupoCategoria): boolean {
    const todas = [...grupo.masculino, ...grupo.femenino];
    return todas.length > 0 && todas.every(c => this.categoriasSeleccionadas().has(c.id));
  }

  toggleCategoria(id: number): void {
    const s = new Set(this.categoriasSeleccionadas());
    if (s.has(id)) s.delete(id); else s.add(id);
    this.categoriasSeleccionadas.set(s);
  }

  toggleGrupo(grupo: GrupoCategoria): void {
    const todas = [...grupo.masculino, ...grupo.femenino];
    const s = new Set(this.categoriasSeleccionadas());
    if (this.grupoTodosSeleccionados(grupo)) {
      todas.forEach(c => s.delete(c.id));
    } else {
      todas.forEach(c => s.add(c.id));
    }
    this.categoriasSeleccionadas.set(s);
  }

  estaSeleccionada(id: number): boolean {
    return this.categoriasSeleccionadas().has(id);
  }

  // ── Modales ──────────────────────────────────────────────
  abrirModalCrear(): void {
    this.modoEdicion.set(false);
    this.campeonatoEditId.set(null);
    this.form.reset({ estado: 'futuro', nivel: 'nacional' });
    this.categoriasSeleccionadas.set(new Set());
    this.modalAbierto.set(true);
  }

  async abrirModalEditar(c: Campeonato): Promise<void> {
    this.modoEdicion.set(true);
    this.campeonatoEditId.set(c.id_campeonato);
    this.form.patchValue({
      nombre:      c.nombre,
      fechaInicio: this.toInputDate(c.fechaInicio),
      fechaFin:    this.toInputDate(c.fechaFin),
      ubicacion:   c.ubicacion,
      estado:      c.estado,
      nivel:       c.nivel,
      descripcion: c.descripcion ?? '',
      urlPortada:  c.urlPortada,
    });
    // Cargar las categorías que ya tiene este campeonato
    try {
      const catsActuales = await this.catSvc.getCategoriasPorCampeonato(c.id_campeonato);
      this.categoriasSeleccionadas.set(new Set(catsActuales.map(cat => cat.id)));
    } catch {
      this.categoriasSeleccionadas.set(new Set());
    }
    this.modalAbierto.set(true);
  }

  cerrarModal(): void {
    this.modalAbierto.set(false);
    this.form.reset();
    this.categoriasSeleccionadas.set(new Set());
  }

  async guardar(): Promise<void> {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }

    this.saving.set(true);
    this.error.set(null);

    const payload = { ...this.form.value, descripcion: this.form.value.descripcion || null };

    try {
      let campeonatoGuardado: Campeonato;

      if (this.modoEdicion() && this.campeonatoEditId() !== null) {
        campeonatoGuardado = await this.svc.updateCampeonato(this.campeonatoEditId()!, payload);
        this.mostrarExito('Campeonato actualizado correctamente');
      } else {
        campeonatoGuardado = await this.svc.createCampeonato(payload);
        this.mostrarExito('Campeonato creado correctamente');
      }

      // Sincronizar categorías: primero eliminar las que ya no están, luego añadir las nuevas
      await this.sincronizarCategorias(campeonatoGuardado.id_campeonato);

      this.cerrarModal();
      await this.cargarCampeonatos();
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al guardar');
    } finally {
      this.saving.set(false);
    }
  }

  private async sincronizarCategorias(idCampeonato: number): Promise<void> {
    const apiBase = `http://localhost:8080/api/campeonatos/${idCampeonato}/categorias`;

    // Categorías actuales del campeonato en BD
    let actuales: number[] = [];
    try {
      const r = await fetch(apiBase);
      if (r.ok) {
        const cats: Categoria[] = await r.json();
        actuales = cats.map(c => c.id);
      }
    } catch { /* si falla, asumimos vacío */ }

    const deseadas = Array.from(this.categoriasSeleccionadas());

    // Eliminar las que ya no se quieren
    const aEliminar = actuales.filter(id => !deseadas.includes(id));
    for (const id of aEliminar) {
      await fetch(`${apiBase}/${id}`, { method: 'DELETE' });
    }

    // Añadir las nuevas
    const aAnadir = deseadas.filter(id => !actuales.includes(id));
    for (const id of aAnadir) {
      await fetch(`${apiBase}/${id}`, { method: 'POST' });
    }
  }

  confirmarBorrar(c: Campeonato): void {
    this.campeonatoABorrar.set(c);
    this.modalBorrarAbierto.set(true);
  }

  cancelarBorrar(): void {
    this.modalBorrarAbierto.set(false);
    this.campeonatoABorrar.set(null);
  }

  async ejecutarBorrar(): Promise<void> {
    const c = this.campeonatoABorrar();
    if (!c) return;
    this.saving.set(true);
    try {
      await this.svc.deleteCampeonato(c.id_campeonato);
      this.mostrarExito(`"${c.nombre}" eliminado correctamente`);
      this.cancelarBorrar();
      await this.cargarCampeonatos();
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al eliminar');
      this.cancelarBorrar();
    } finally {
      this.saving.set(false);
    }
  }

  cerrarSesion(): void {
    this.auth.logout();
  }

  // ── Helpers ──────────────────────────────────────────────
  private mostrarExito(msg: string): void {
    this.successMsg.set(msg);
    setTimeout(() => this.successMsg.set(null), 3500);
  }

  private toInputDate(dateStr: string): string {
    return new Date(dateStr).toISOString().split('T')[0];
  }

  formatDate(date: string): string {
    return new Date(date).toLocaleDateString('es-ES', { day: '2-digit', month: 'short', year: 'numeric' });
  }

  badgeClass(estado: Estado): string {
    return { futuro: 'badge-futuro', activo: 'badge-activo', pasado: 'badge-pasado' }[estado];
  }

  estadoLabel(estado: Estado): string {
    return { futuro: 'Próximo', activo: 'En curso', pasado: 'Finalizado' }[estado];
  }

  get f() { return this.form.controls; }
  get usuarioActual() { return this.auth.currentUser(); }
}
