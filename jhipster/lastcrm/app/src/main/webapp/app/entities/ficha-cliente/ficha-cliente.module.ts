import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LastcrmSharedModule } from 'app/shared/shared.module';
import { FichaClienteComponent } from './ficha-cliente.component';
import { FichaClienteDetailComponent } from './ficha-cliente-detail.component';
import { FichaClienteUpdateComponent } from './ficha-cliente-update.component';
import { FichaClienteDeletePopupComponent, FichaClienteDeleteDialogComponent } from './ficha-cliente-delete-dialog.component';
import { fichaClienteRoute, fichaClientePopupRoute } from './ficha-cliente.route';

const ENTITY_STATES = [...fichaClienteRoute, ...fichaClientePopupRoute];

@NgModule({
  imports: [LastcrmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FichaClienteComponent,
    FichaClienteDetailComponent,
    FichaClienteUpdateComponent,
    FichaClienteDeleteDialogComponent,
    FichaClienteDeletePopupComponent
  ],
  entryComponents: [FichaClienteComponent, FichaClienteUpdateComponent, FichaClienteDeleteDialogComponent, FichaClienteDeletePopupComponent]
})
export class LastcrmFichaClienteModule {}
