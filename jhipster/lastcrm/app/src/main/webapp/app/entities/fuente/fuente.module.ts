import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LastcrmSharedModule } from 'app/shared/shared.module';
import { FuenteComponent } from './fuente.component';
import { FuenteDetailComponent } from './fuente-detail.component';
import { FuenteUpdateComponent } from './fuente-update.component';
import { FuenteDeletePopupComponent, FuenteDeleteDialogComponent } from './fuente-delete-dialog.component';
import { fuenteRoute, fuentePopupRoute } from './fuente.route';

const ENTITY_STATES = [...fuenteRoute, ...fuentePopupRoute];

@NgModule({
  imports: [LastcrmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [FuenteComponent, FuenteDetailComponent, FuenteUpdateComponent, FuenteDeleteDialogComponent, FuenteDeletePopupComponent],
  entryComponents: [FuenteComponent, FuenteUpdateComponent, FuenteDeleteDialogComponent, FuenteDeletePopupComponent]
})
export class LastcrmFuenteModule {}
