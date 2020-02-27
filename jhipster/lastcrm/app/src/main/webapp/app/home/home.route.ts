import { Routes } from '@angular/router';

import { HomeComponent } from './home.component';
import { JhiResolvePagingParams } from 'ng-jhipster';

export const HOME_ROUTES: Routes = [
  {
    path: '',
    component: HomeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [],
      defaultSort: 'id,asc',
      pageTitle: 'home.title'
    }
  },
  {
    path: 'home',
    component: HomeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [],
      defaultSort: 'id,asc',
      pageTitle: 'home.title'
    }
  }
];
