import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Fuente } from 'app/shared/model/fuente.model';
import { FuenteService } from './fuente.service';
import { FuenteComponent } from './fuente.component';
import { FuenteDetailComponent } from './fuente-detail.component';
import { FuenteUpdateComponent } from './fuente-update.component';
import { FuenteDeletePopupComponent } from './fuente-delete-dialog.component';
import { IFuente } from 'app/shared/model/fuente.model';

@Injectable({ providedIn: 'root' })
export class FuenteResolve implements Resolve<IFuente> {
  constructor(private service: FuenteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFuente> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Fuente>) => response.ok),
        map((fuente: HttpResponse<Fuente>) => fuente.body)
      );
    }
    return of(new Fuente());
  }
}

export const fuenteRoute: Routes = [
  {
    path: '',
    component: FuenteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'lastcrmApp.fuente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FuenteDetailComponent,
    resolve: {
      fuente: FuenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.fuente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FuenteUpdateComponent,
    resolve: {
      fuente: FuenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.fuente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FuenteUpdateComponent,
    resolve: {
      fuente: FuenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.fuente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fuentePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FuenteDeletePopupComponent,
    resolve: {
      fuente: FuenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.fuente.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
