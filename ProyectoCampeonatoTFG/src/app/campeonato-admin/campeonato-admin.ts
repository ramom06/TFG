import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { CampeonatoService } from '../service/campeonato-service';
import { CategoriaService } from '../service/categoria-service';
import {AutenticacionService} from '../service/autenticacion-service';
import { Campeonato, Estado, EstadoVisual, Nivel } from '../interfaces/campeonato';
import { Categoria } from '../interfaces/categoria';
import { environment } from '../../environments/environment';

// Grupo de categorías para el selector del formulario
export interface GrupoCategoria {
  grupo: string;
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

  todasCategorias = signal<Categoria[]>([]);

  categoriasSeleccionadas = signal<Set<number>>(new Set());

  gruposCategorias = computed<GrupoCategoria[]>(() => this.agruparCategorias(this.todasCategorias()));

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
    private camptService: CampeonatoService,
    private catService: CategoriaService,
    private auth: AutenticacionService,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.buildForm();
    this.cargarCampeonatos();
    this.cargarTodasCategorias();
  }

  private buildForm(): void {
    this.form = this.formBuilder.group({
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
      const data = await this.camptService.getAllCampeonatos();
      this.campeonatos.set(data.sort((a, b) => new Date(b.fechaInicio).getTime() - new Date(a.fechaInicio).getTime()));
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al cargar campeonatos');
    } finally {
      this.loading.set(false);
    }
  }

  async cargarTodasCategorias(): Promise<void> {
    try {
      const response = await fetch(`${environment.apiUrl}/api/categorias`);
      if (!response.ok) throw new Error();
      const data: Categoria[] = await response.json();
      this.todasCategorias.set(data);
    } catch {}
  }
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
    return todas.length > 0 && todas.every(c => this.categoriasSeleccionadas().has(c.idCategoria));
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
      todas.forEach(c => s.delete(c.idCategoria));
    } else {
      todas.forEach(c => s.add(c.idCategoria));
    }
    this.categoriasSeleccionadas.set(s);
  }

  estaSeleccionada(id: number): boolean {
    return this.categoriasSeleccionadas().has(id);
  }

  abrirModalCrear(): void {
    this.modoEdicion.set(false);
    this.campeonatoEditId.set(null);
    this.form.reset({ estado: 'futuro', nivel: 'nacional' });
    this.categoriasSeleccionadas.set(new Set());
    this.modalAbierto.set(true);
  }

  async abrirModalEditar(c: Campeonato): Promise<void> {
    this.modoEdicion.set(true);
    this.campeonatoEditId.set(c.idCampeonato);
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
    try {
      const catsActuales = await this.catService.getCategoriasPorCampeonato(c.idCampeonato);
      this.categoriasSeleccionadas.set(new Set(catsActuales.map(cat => cat.idCategoria)));
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

    const preparoDatos = { ...this.form.value, descripcion: this.form.value.descripcion || null };

    try {
      let campeonatoGuardado: Campeonato;

      if (this.modoEdicion() && this.campeonatoEditId() !== null) {
        campeonatoGuardado = await this.camptService.updateCampeonato(this.campeonatoEditId()!, preparoDatos);
        this.mostrarExito('Campeonato actualizado correctamente');
      } else {
        campeonatoGuardado = await this.camptService.createCampeonato(preparoDatos);
        this.mostrarExito('Campeonato creado correctamente');
      }

      await this.sincronizarCategorias(campeonatoGuardado.idCampeonato);

      this.cerrarModal();
      await this.cargarCampeonatos();
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al guardar');
    } finally {
      this.saving.set(false);
    }
  }

  //Mantener relacion Campeonato - Categoria
  private async sincronizarCategorias(idCampeonato: number): Promise<void> {
    const apiBase = `${environment.apiUrl}/api/campeonatos/${idCampeonato}/categorias`;

    // Categorías actuales del campeonato en BD
    let actuales: number[] = [];
    try {
      const r = await fetch(apiBase);
      if (r.ok) {
        const cats: Categoria[] = await r.json();
        actuales = cats.map(c => c.idCategoria);
      }
    } catch {}

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
      await this.camptService.deleteCampeonato(c.idCampeonato);
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

  // Estado visual calculado por fechas reales
  estadoCalculado(c: Campeonato): EstadoVisual {
    const hoy = new Date(); hoy.setHours(0, 0, 0, 0);
    const inicio = new Date(c.fechaInicio); inicio.setHours(0, 0, 0, 0);
    const fin    = new Date(c.fechaFin);    fin.setHours(0, 0, 0, 0);
    if (fin < hoy)    return 'pasado';
    if (inicio > hoy) return 'futuro';
    return 'activo';
  }

  badgeClass(estado: EstadoVisual): string {
    return {
      futuro: 'badge-futuro',
      activo: 'badge-activo',
      pasado: 'badge-pasado',
    }[estado];
  }

  estadoLabel(estado: EstadoVisual): string {
    return {
      futuro: 'Próximo',
      activo: 'En curso',
      pasado: 'Finalizado',
    }[estado];
  }

  sorteoHecho(c: Campeonato): boolean {
    return c.estado === 'inscripciones_cerradas' || c.estado === 'pasado';
  }


  // Id del campeonato
  idCampeonatoSorteado = signal<number | null>(null);

  async sortearPrimeraRonda(c: Campeonato): Promise<void> {
    if (this.idCampeonatoSorteado() !== null) return;

    if (!confirm(`Sortear primera ronda para "${c.nombre}"?\nLas categorías que ya tengan combates no se modificarán.`)) return;

    this.idCampeonatoSorteado.set(c.idCampeonato);
    this.error.set(null);

    try {
      const actualizado = await this.camptService.sortearPrimeraRonda(c.idCampeonato);
      this.actualizarEnLista(actualizado);
      this.mostrarExito(`Primera ronda sorteada para "${c.nombre}"`);
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al generar la primera ronda');
    } finally {
      this.idCampeonatoSorteado.set(null);
    }
  }

  async sortearCompleto(c: Campeonato): Promise<void> {
    if (this.idCampeonatoSorteado() !== null) return;

    if (!confirm(`Sortear el campeonato completo (hasta el ganador) "${c.nombre}"?`)) return;

    this.idCampeonatoSorteado.set(c.idCampeonato);
    this.error.set(null);

    try {
      const actualizado = await this.camptService.sortearCompleto(c.idCampeonato);
      this.actualizarEnLista(actualizado);
      this.mostrarExito(`Sorteo completo generado para "${c.nombre}"`);
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al generar el sorteo completo');
    } finally {
      this.idCampeonatoSorteado.set(null);
    }
  }

  private actualizarEnLista(c: Campeonato): void {
    this.campeonatos.update(list =>
      list.map(x => x.idCampeonato === c.idCampeonato ? c : x)
    );
  }

  get f() { return this.form.controls; }
  get usuarioActual() { return this.auth.currentUser(); }
}
