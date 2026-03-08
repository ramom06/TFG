import { Routes } from '@angular/router';
import { CampeonatoDetalleComponent } from './campeonato-detalle/campeonato-detalle';
import { CampeonatoListComponent } from './campeonato-list/campeonato-list'; // ajusta el path a tu componente lista

export const routes: Routes = [
  { path: '', component: CampeonatoListComponent, title: 'Campeonatos' },
  { path: 'campeonato/:id', component: CampeonatoDetalleComponent, title: 'Detalle Campeonato' },
];
