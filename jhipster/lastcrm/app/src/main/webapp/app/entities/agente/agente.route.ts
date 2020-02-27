import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Agente } from 'app/shared/model/agente.model';
import { AgenteService } from './agente.service';
import { AgenteComponent } from './agente.component';
import { AgenteDetailComponent } from './agente-detail.component';
import { AgenteUpdateComponent } from './agente-update.component';
import { AgenteDeletePopupComponent } from './agente-delete-dialog.component';
import { IAgente } from 'app/shared/model/agente.model';

@Injectable({ providedIn: 'root' })
export class AgenteResolve implements Resolve<IAgente> {
  constructor(private service: AgenteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAgente> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Agente>) => response.ok),
        map((agente: HttpResponse<Agente>) => agente.body)
      );
    }
    return of(new Agente());
  }
}

export const agenteRoute: Routes = [
  {
    path: '',
    component: AgenteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'lastcrmApp.agente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AgenteDetailComponent,
    resolve: {
      agente: AgenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.agente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AgenteUpdateComponent,
    resolve: {
      agente: AgenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.agente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AgenteUpdateComponent,
    resolve: {
      agente: AgenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.agente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const agentePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AgenteDeletePopupComponent,
    resolve: {
      agente: AgenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.agente.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
