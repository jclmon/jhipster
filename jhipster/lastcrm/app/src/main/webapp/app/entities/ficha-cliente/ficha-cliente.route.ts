import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FichaCliente } from 'app/shared/model/ficha-cliente.model';
import { FichaClienteService } from './ficha-cliente.service';
import { FichaClienteComponent } from './ficha-cliente.component';
import { FichaClienteDetailComponent } from './ficha-cliente-detail.component';
import { FichaClienteUpdateComponent } from './ficha-cliente-update.component';
import { FichaClienteDeletePopupComponent } from './ficha-cliente-delete-dialog.component';
import { IFichaCliente } from 'app/shared/model/ficha-cliente.model';

@Injectable({ providedIn: 'root' })
export class FichaClienteResolve implements Resolve<IFichaCliente> {
  constructor(private service: FichaClienteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFichaCliente> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FichaCliente>) => response.ok),
        map((fichaCliente: HttpResponse<FichaCliente>) => fichaCliente.body)
      );
    }
    return of(new FichaCliente());
  }
}

export const fichaClienteRoute: Routes = [
  {
    path: '',
    component: FichaClienteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'lastcrmApp.fichaCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FichaClienteDetailComponent,
    resolve: {
      fichaCliente: FichaClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.fichaCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FichaClienteUpdateComponent,
    resolve: {
      fichaCliente: FichaClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.fichaCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FichaClienteUpdateComponent,
    resolve: {
      fichaCliente: FichaClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.fichaCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fichaClientePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FichaClienteDeletePopupComponent,
    resolve: {
      fichaCliente: FichaClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.fichaCliente.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
