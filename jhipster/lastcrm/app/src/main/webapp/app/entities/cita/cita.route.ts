import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cita } from 'app/shared/model/cita.model';
import { CitaService } from './cita.service';
import { CitaComponent } from './cita.component';
import { CitaDetailComponent } from './cita-detail.component';
import { CitaUpdateComponent } from './cita-update.component';
import { CitaDeletePopupComponent } from './cita-delete-dialog.component';
import { ICita } from 'app/shared/model/cita.model';

@Injectable({ providedIn: 'root' })
export class CitaResolve implements Resolve<ICita> {
  constructor(private service: CitaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICita> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Cita>) => response.ok),
        map((cita: HttpResponse<Cita>) => cita.body)
      );
    }
    return of(new Cita());
  }
}

export const citaRoute: Routes = [
  {
    path: '',
    component: CitaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'lastcrmApp.cita.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CitaDetailComponent,
    resolve: {
      cita: CitaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.cita.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CitaUpdateComponent,
    resolve: {
      cita: CitaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.cita.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CitaUpdateComponent,
    resolve: {
      cita: CitaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.cita.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const citaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CitaDeletePopupComponent,
    resolve: {
      cita: CitaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lastcrmApp.cita.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
