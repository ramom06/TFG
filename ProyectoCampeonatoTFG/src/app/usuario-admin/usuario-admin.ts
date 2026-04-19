import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { AutenticacionService } from '../service/autenticacion-service';
import { UsuarioService } from '../service/usuario-service';
import { Usuario, Rol } from '../interfaces/usuario';

@Component({
  selector: 'app-usuario-admin',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterLink],
  templateUrl: './usuario-admin.html',
  styleUrl: './usuario-admin.css',
})
export class UsuarioAdminComponent implements OnInit {

  usuarios    = signal<Usuario[]>([]);
  loading     = signal(true);
  saving      = signal(false);
  error       = signal<string | null>(null);
  successMsg  = signal<string | null>(null);

  // Modal crear/editar
  modalAbierto  = signal(false);
  modoEdicion   = signal(false);
  usuarioEditId = signal<number | null>(null);

  // Modal confirmación borrado
  modalBorrarAbierto = signal(false);
  usuarioABorrar     = signal<Usuario | null>(null);

  // Filtros
  searchText  = signal('');
  filtroRol   = signal<string>('TODOS');

  filteredUsuarios = computed(() => {
    const txt = this.searchText().toLowerCase();
    const rol = this.filtroRol();
    return this.usuarios().filter(u => {
      const matchRol  = rol === 'TODOS' || u.rol === rol;
      const matchText = !txt ||
        u.nombre.toLowerCase().includes(txt) ||
        u.apellidos.toLowerCase().includes(txt) ||
        u.dni.toLowerCase().includes(txt) ||
        u.email.toLowerCase().includes(txt);
      return matchRol && matchText;
    });
  });

  form!: FormGroup;

  get usuarioActual() { return this.auth.currentUser(); }
  get f() { return this.form.controls; }

  roles: Array<{ value: string; label: string }> = [
    { value: 'ADMIN',      label: 'Administrador' },
    { value: 'ARBITRO',    label: 'Árbitro'        },
    { value: 'COMPETIDOR', label: 'Competidor'     },
  ];

  constructor(
    private auth:    AutenticacionService,
    private svc:     UsuarioService,
    private fb:      FormBuilder,
    private router:  Router,
  ) {}

  async ngOnInit(): Promise<void> {
    this.buildForm();
    await this.cargarUsuarios();
  }

  // ── Formulario ──────────────────────────────────────────────

  private buildForm(u?: Usuario): void {
    this.form = this.fb.group({
      nombre:          [u?.nombre          ?? '', [Validators.required, Validators.minLength(2)]],
      apellidos:       [u?.apellidos       ?? '', [Validators.required]],
      dni:             [u?.dni             ?? '', [Validators.required, Validators.pattern(/^\d{8}[A-Za-z]$/)]],
      email:           [u?.email           ?? '', [Validators.required, Validators.email]],
      password:        ['', this.modoEdicion() ? [] : [Validators.required, Validators.minLength(6)]],
      rol:             [u?.rol             ?? Rol.COMPETIDOR, Validators.required],
      genero:          [u?.genero          ?? 'M', Validators.required],
      fechaNacimiento: [u?.fechaNacimiento ? u.fechaNacimiento.toString().substring(0,10) : '', Validators.required],
    });
  }

  // ── Carga ────────────────────────────────────────────────────

  async cargarUsuarios(): Promise<void> {
    this.loading.set(true);
    this.error.set(null);
    try {
      const lista = await this.svc.getAll();
      this.usuarios.set(lista);
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al cargar usuarios');
    } finally {
      this.loading.set(false);
    }
  }

  // ── Modal crear ──────────────────────────────────────────────

  abrirModalCrear(): void {
    this.modoEdicion.set(false);
    this.usuarioEditId.set(null);
    this.buildForm();
    this.modalAbierto.set(true);
  }

  abrirModalEditar(u: Usuario): void {
    this.modoEdicion.set(true);
    this.usuarioEditId.set(u.idUsuario);
    this.buildForm(u);
    // En edición la contraseña es opcional
    this.f['password'].clearValidators();
    this.f['password'].updateValueAndValidity();
    this.modalAbierto.set(true);
  }

  cerrarModal(): void {
    this.modalAbierto.set(false);
    this.error.set(null);
  }

  // ── Guardar ──────────────────────────────────────────────────

  async guardar(): Promise<void> {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.saving.set(true);
    this.error.set(null);

    try {
      const raw = this.form.value;
      const payload: Partial<Usuario> = {
        nombre:          raw.nombre.trim(),
        apellidos:       raw.apellidos.trim(),
        dni:             raw.dni.trim().toUpperCase(),
        email:           raw.email.trim(),
        rol:             raw.rol,
        genero:          raw.genero,
        fechaNacimiento: raw.fechaNacimiento,
      };
      if (raw.password) payload.password = raw.password;

      if (this.modoEdicion()) {
        const updated = await this.svc.update(this.usuarioEditId()!, payload);
        this.usuarios.update(list => list.map(u => u.idUsuario === updated.idUsuario ? updated : u));
        this.mostrarExito('Usuario actualizado correctamente');
      } else {
        const created = await this.svc.create(payload);
        this.usuarios.update(list => [created, ...list]);
        this.mostrarExito('Usuario creado correctamente');
      }

      this.cerrarModal();
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al guardar');
    } finally {
      this.saving.set(false);
    }
  }

  // ── Borrar ───────────────────────────────────────────────────

  confirmarBorrar(u: Usuario): void {
    this.usuarioABorrar.set(u);
    this.modalBorrarAbierto.set(true);
  }

  cancelarBorrar(): void {
    this.modalBorrarAbierto.set(false);
    this.usuarioABorrar.set(null);
  }

  async eliminarUsuario(): Promise<void> {
    const u = this.usuarioABorrar();
    if (!u) return;

    this.saving.set(true);
    try {
      await this.svc.delete(u.idUsuario);
      this.usuarios.update(list => list.filter(x => x.idUsuario !== u.idUsuario));
      this.mostrarExito('Usuario eliminado correctamente');
      this.cancelarBorrar();
    } catch (e: any) {
      this.error.set(e.message ?? 'Error al eliminar');
    } finally {
      this.saving.set(false);
    }
  }

  // ── Helpers ──────────────────────────────────────────────────

  private mostrarExito(msg: string): void {
    this.successMsg.set(msg);
    setTimeout(() => this.successMsg.set(null), 3500);
  }

  cerrarSesion(): void {
    this.auth.logout();
    this.router.navigate(['/']);
  }

  rolLabel(rol: string): string {
    return this.roles.find(r => r.value === rol)?.label ?? rol;
  }

  rolColor(rol: string): string {
    switch (rol) {
      case 'ADMIN':      return 'text-[#e01c2e] bg-[#e01c2e]/10 border-[#e01c2e]/20';
      case 'ARBITRO':    return 'text-amber-400 bg-amber-400/10 border-amber-400/20';
      case 'COMPETIDOR': return 'text-sky-400 bg-sky-400/10 border-sky-400/20';
      default:           return 'text-[#8b95a3] bg-white/5 border-white/10';
    }
  }

  esCampoInvalido(campo: string): boolean {
    const ctrl = this.f[campo];
    return ctrl.invalid && ctrl.touched;
  }
}
