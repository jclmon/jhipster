import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'agente',
        loadChildren: () => import('./agente/agente.module').then(m => m.LastcrmAgenteModule)
      },
      {
        path: 'area',
        loadChildren: () => import('./area/area.module').then(m => m.LastcrmAreaModule)
      },
      {
        path: 'cita',
        loadChildren: () => import('./cita/cita.module').then(m => m.LastcrmCitaModule)
      },
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.LastcrmClienteModule)
      },
      {
        path: 'ficha-cliente',
        loadChildren: () => import('./ficha-cliente/ficha-cliente.module').then(m => m.LastcrmFichaClienteModule)
      },
      {
        path: 'fuente',
        loadChildren: () => import('./fuente/fuente.module').then(m => m.LastcrmFuenteModule)
      },
      {
        path: 'municipio',
        loadChildren: () => import('./municipio/municipio.module').then(m => m.LastcrmMunicipioModule)
      },
      {
        path: 'pais',
        loadChildren: () => import('./pais/pais.module').then(m => m.LastcrmPaisModule)
      },
      {
        path: 'provincia',
        loadChildren: () => import('./provincia/provincia.module').then(m => m.LastcrmProvinciaModule)
      },
      {
        path: 'producto',
        loadChildren: () => import('./producto/producto.module').then(m => m.LastcrmProductoModule)
      },
      {
        path: 'tipo-producto',
        loadChildren: () => import('./tipo-producto/tipo-producto.module').then(m => m.LastcrmTipoProductoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: []
})
export class LastcrmEntityModule {}
