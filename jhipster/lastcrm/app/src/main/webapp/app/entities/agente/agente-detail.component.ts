import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgente } from 'app/shared/model/agente.model';

@Component({
  selector: 'jhi-agente-detail',
  templateUrl: './agente-detail.component.html'
})
export class AgenteDetailComponent implements OnInit {
  agente: IAgente;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agente }) => {
      this.agente = agente;
    });
  }

  previousState() {
    window.history.back();
  }
}
