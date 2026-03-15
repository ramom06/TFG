import { Routes } from '@angular/router';
import { CampeonatoDetalleComponent } from './campeonato-detalle/campeonato-detalle';
import { CampeonatoListComponent }    from './campeonato-list/campeonato-list';
import { CampeonatoAdminComponent }   from './campeonato-admin/campeonato-admin';
import { AdminLoginComponent }        from './admin-login/admin-login';
import { guards }                 from './guards/guards';

export const routes: Routes = [
  { path: '', component: CampeonatoListComponent, title: 'Campeonatos' },
  { path: 'campeonato/:id', component: CampeonatoDetalleComponent, title: 'Detalle Campeonato' },
  { path: 'admin/login', component: AdminLoginComponent, title: 'Acceso Admin' },
  { path: 'admin/campeonatos', component: CampeonatoAdminComponent, title: 'Gestión de Campeonatos', canActivate: [guards] },
];
