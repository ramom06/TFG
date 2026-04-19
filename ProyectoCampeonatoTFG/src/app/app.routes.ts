import { Routes } from '@angular/router';
import { CampeonatoDetalle }        from './campeonato-detalle/campeonato-detalle';
import { CampeonatoListComponent }  from './campeonato-list/campeonato-list';
import { CampeonatoAdminComponent } from './campeonato-admin/campeonato-admin';
import { AdminLoginComponent }      from './admin-login/admin-login';
import { SorteoComponent }          from './sorteo/sorteo';  // ← ruta real en el repo
import { guards }                   from './guards/guards';
import {UsuarioAdmin} from './usuario-admin/usuario-admin';

export const routes: Routes = [
  { path: '', component: CampeonatoListComponent, title: 'Campeonatos' },
  { path: 'campeonato/:id', component: CampeonatoDetalle, title: 'Detalle Campeonato' },
  {
    path: 'campeonato/:id/categoria/:idCategoria/sorteo',
    component: SorteoComponent,
    title: 'Sorteo'
  },
  { path: 'admin/login', component: AdminLoginComponent, title: 'Acceso Admin' },
  {
    path: 'admin/campeonatos',
    component: CampeonatoAdminComponent,
    title: 'Gestión de Campeonatos',
    canActivate: [guards]
  },
  {
    path: 'admin/usuarios',
    component: UsuarioAdmin,
    title: 'Gestión de Usuarios',
    canActivate: [guards]   // misma protección que campeonatos
  },
];
