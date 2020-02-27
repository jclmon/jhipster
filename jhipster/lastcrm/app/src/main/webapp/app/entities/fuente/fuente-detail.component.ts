import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFuente } from 'app/shared/model/fuente.model';

@Component({
  selector: 'jhi-fuente-detail',
  templateUrl: './fuente-detail.component.html'
})
export class FuenteDetailComponent implements OnInit {
  fuente: IFuente;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fuente }) => {
      this.fuente = fuente;
    });
  }

  previousState() {
    window.history.back();
  }
}
