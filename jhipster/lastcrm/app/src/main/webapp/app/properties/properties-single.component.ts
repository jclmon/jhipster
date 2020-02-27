import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {IProducto} from "app/shared/model/producto.model";

@Component({
  selector: 'jhi-properties-single',
  templateUrl: './properties-single.component.html',
  styleUrls: ['./properties-single.component.scss']
})
export class PropertiesSingleComponent implements OnInit {
  producto: IProducto;

  constructor(protected activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({producto}) => {
      this.producto = producto;
    });
  }

  previousState() {
    window.history.back();
  }

}
