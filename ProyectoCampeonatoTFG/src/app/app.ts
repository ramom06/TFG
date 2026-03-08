import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {CampeonatoListComponent} from './campeonato-list/campeonato-list';

@Component({
  selector: 'app-root',
  imports: [CampeonatoListComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('ProyectoCampeonatoTFG');
}
