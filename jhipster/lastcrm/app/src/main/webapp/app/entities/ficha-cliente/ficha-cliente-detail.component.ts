import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFichaCliente } from 'app/shared/model/ficha-cliente.model';

@Component({
  selector: 'jhi-ficha-cliente-detail',
  templateUrl: './ficha-cliente-detail.component.html'
})
export class FichaClienteDetailComponent implements OnInit {
  fichaCliente: IFichaCliente;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fichaCliente }) => {
      this.fichaCliente = fichaCliente;
    });
  }

  previousState() {
    window.history.back();
  }
}
