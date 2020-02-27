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

import { ProvinciaPage } from './provincia';
import { ProvinciaUpdatePage } from './provincia-update';
import { Provincia, ProvinciaService, ProvinciaDetailPage } from '.';

@Injectable({ providedIn: 'root' })
export class ProvinciaResolve implements Resolve<Provincia> {
  constructor(private service: ProvinciaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Provincia> {
    const id = route.params.id ? route.params.id : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Provincia>) => response.ok),
        map((provincia: HttpResponse<Provincia>) => provincia.body)
      );
    }
    return of(new Provincia());
  }
}

const routes: Routes = [
    {
      path: '',
      component: ProvinciaPage,
      data: {
        authorities: ['ROLE_USER']
      },
      canActivate: [UserRouteAccessService]
    },
    {
      path: 'new',
      component: ProvinciaUpdatePage,
      resolve: {
        data: ProvinciaResolve
      },
      data: {
        authorities: ['ROLE_USER']
      },
      canActivate: [UserRouteAccessService]
    },
    {
      path: ':id/view',
      component: ProvinciaDetailPage,
      resolve: {
        data: ProvinciaResolve
      },
      data: {
        authorities: ['ROLE_USER']
      },
      canActivate: [UserRouteAccessService]
    },
    {
      path: ':id/edit',
      component: ProvinciaUpdatePage,
      resolve: {
        data: ProvinciaResolve
      },
      data: {
        authorities: ['ROLE_USER']
      },
      canActivate: [UserRouteAccessService]
    }
  ];


@NgModule({
    declarations: [
        ProvinciaPage,
        ProvinciaUpdatePage,
        ProvinciaDetailPage
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
export class ProvinciaPageModule {
}
