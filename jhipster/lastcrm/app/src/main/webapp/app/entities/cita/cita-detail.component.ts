import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICita } from 'app/shared/model/cita.model';

@Component({
  selector: 'jhi-cita-detail',
  templateUrl: './cita-detail.component.html'
})
export class CitaDetailComponent implements OnInit {
  cita: ICita;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cita }) => {
      this.cita = cita;
    });
  }

  previousState() {
    window.history.back();
  }
}
