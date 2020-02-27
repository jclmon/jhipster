import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';

import {PropertiesComponent} from './properties.component';
import {JhiResolvePagingParams} from 'ng-jhipster';
import {PropertiesSingleComponent} from 'app/properties/properties-single.component';
import {Injectable} from "@angular/core";
import {Observable, of} from "rxjs";
import {filter, map} from "rxjs/operators";
import {HttpResponse} from "@angular/common/http";
import {IProducto, Producto} from "app/shared/model/producto.model";
import {ProductoService} from "app/entities/producto/producto.service";

@Injectable({ providedIn: 'root' })
export class PropertiesResolve implements Resolve<IProducto> {
  constructor(private service: ProductoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProducto> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Producto>) => response.ok),
        map((producto: HttpResponse<Producto>) => producto.body)
      );
    }
    return of(new Producto());
  }
}

export const PROPERTIES_ROUTES: Routes = [
  {
    path: '',
    component: PropertiesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [],
      defaultSort: 'id,asc',
      pageTitle: 'lastcrmApp.producto.home.title'
    }
  },
  {
    path: 'properties',
    component: PropertiesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [],
      defaultSort: 'id,asc',
      pageTitle: 'lastcrmApp.producto.home.title'
    }
  },
  {
    path: ':id/view',
    component: PropertiesSingleComponent,
    resolve: {
      producto: PropertiesResolve
    },
    data: {
      authorities: [],
      pageTitle: 'lastcrmApp.producto.home.title'
    },
    canActivate: []
  }
];
