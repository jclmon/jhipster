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

import { MunicipioPage } from './municipio';
import { MunicipioUpdatePage } from './municipio-update';
import { Municipio, MunicipioService, MunicipioDetailPage } from '.';

@Injectable({ providedIn: 'root' })
export class MunicipioResolve implements Resolve<Municipio> {
  constructor(private service: MunicipioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Municipio> {
    const id = route.params.id ? route.params.id : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Municipio>) => response.ok),
        map((municipio: HttpResponse<Municipio>) => municipio.body)
      );
    }
    return of(new Municipio());
  }
}

const routes: Routes = [
    {
      path: '',
      component: MunicipioPage,
      data: {
        authorities: ['ROLE_USER']
      },
      canActivate: [UserRouteAccessService]
    },
    {
      path: 'new',
      component: MunicipioUpdatePage,
      resolve: {
        data: MunicipioResolve
      },
      data: {
        authorities: ['ROLE_USER']
      },
      canActivate: [UserRouteAccessService]
    },
    {
      path: ':id/view',
      component: MunicipioDetailPage,
      resolve: {
        data: MunicipioResolve
      },
      data: {
        authorities: ['ROLE_USER']
      },
      canActivate: [UserRouteAccessService]
    },
    {
      path: ':id/edit',
      component: MunicipioUpdatePage,
      resolve: {
        data: MunicipioResolve
      },
      data: {
        authorities: ['ROLE_USER']
      },
      canActivate: [UserRouteAccessService]
    }
  ];


@NgModule({
    declarations: [
        MunicipioPage,
        MunicipioUpdatePage,
        MunicipioDetailPage
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
export class MunicipioPageModule {
}
