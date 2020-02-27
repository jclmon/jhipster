import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { TranslateModule } from '@ngx-translate/core';
import { UserRouteAccessService } from 'src/app/services/auth/user-route-access.service';
import { EntitiesPage } from './entities.page';

const routes: Routes = [
  {
    path: '',
    component: EntitiesPage,
    data: {
      authorities: ['ROLE_USER']
    },
    canActivate: [UserRouteAccessService]
  }
  , {
    path: 'pais',
    loadChildren: './pais/pais.module#PaisPageModule'
  }
  , {
    path: 'producto',
    loadChildren: './producto/producto.module#ProductoPageModule'
  }
  , {
    path: 'municipio',
    loadChildren: './municipio/municipio.module#MunicipioPageModule'
  }
  , {
    path: 'provincia',
    loadChildren: './provincia/provincia.module#ProvinciaPageModule'
  }
  , {
    path: 'tipo-producto',
    loadChildren: './tipo-producto/tipo-producto.module#TipoProductoPageModule'
  }
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

@NgModule({
  imports: [IonicModule, CommonModule, FormsModule, RouterModule.forChild(routes), TranslateModule],
  declarations: [EntitiesPage]
})
export class EntitiesPageModule {}
