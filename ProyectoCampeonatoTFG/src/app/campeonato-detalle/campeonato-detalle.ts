import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CampeonatoService } from '../campeonato-service';
import { Campeonato } from '../campeonato';
import {CommonModule, formatDate} from '@angular/common';

@Component({
  selector: 'app-campeonato-detalle',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './campeonato-detalle.html',
  styleUrl: './campeonato-detalle.css',
})
export class CampeonatoDetalleComponent implements OnInit {
  campeonato = signal<Campeonato | null>(null);

  constructor(private route: ActivatedRoute, private svc: CampeonatoService) {}

  async ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    const todos = await this.svc.getAllCampeonatos();
    this.campeonato.set(todos.find(c => c.id_campeonato === id) || null);
  }

  protected readonly formatDate = formatDate;
}
