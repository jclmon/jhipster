import { NgModule, Injectable } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { UserRouteAccessService } from '../../../services/auth/user-route-access.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';

import { TipoProductoPage } from './tipo-producto';
import { TipoProductoUpdatePage } from './tipo-producto-update';
import { TipoProducto, TipoProductoService, TipoProductoDetailPage } from '.';

@Injectable({ providedIn: 'root' })
export class TipoProductoResolve implements Resolve<TipoProducto> {
  constructor(private service: TipoProductoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TipoProducto> {
    const id = route.params.id ? route.params.id : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TipoProducto>) => response.ok),
        map((tipoProducto: HttpResponse<TipoProducto>) => tipoProducto.body)
      );
    }
    return of(new TipoProducto());
  }
}

const routes: Routes = [
    {
      path: '',
      component: TipoProductoPage,
      data: {
        authorities: ['ROLE_USER']
      },
      canActivate: [UserRouteAccessService]
    },
    {
      path: 'new',
      component: TipoProductoUpdatePage,
      resolve: {
        data: TipoProductoResolve
      },
      data: {
        authorities: ['ROLE_USER']
      },
      canActivate: [UserRouteAccessService]
    },
    {
      path: ':id/view',
      component: TipoProductoDetailPage,
      resolve: {
        data: TipoProductoResolve
      },
      data: {
        authorities: ['ROLE_USER']
      },
      canActivate: [UserRouteAccessService]
    },
    {
      path: ':id/edit',
      component: TipoProductoUpdatePage,
      resolve: {
        data: TipoProductoResolve
      },
      data: {
        authorities: ['ROLE_USER']
      },
      canActivate: [UserRouteAccessService]
    }
  ];


@NgModule({
    declarations: [
        TipoProductoPage,
        TipoProductoUpdatePage,
        TipoProductoDetailPage
    ],
    imports: [
        IonicModule,
        FormsModule,
        ReactiveFormsModule,
        CommonModule,
        TranslateModule,
        RouterModule.forChild(routes)
    ]
})
export class TipoProductoPageModule {
}
