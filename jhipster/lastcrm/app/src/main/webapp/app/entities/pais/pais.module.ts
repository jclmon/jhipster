import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LastcrmSharedModule } from 'app/shared/shared.module';
import { PaisComponent } from './pais.component';
import { PaisDetailComponent } from './pais-detail.component';
import { PaisUpdateComponent } from './pais-update.component';
import { PaisDeletePopupComponent, PaisDeleteDialogComponent } from './pais-delete-dialog.component';
import { paisRoute, paisPopupRoute } from './pais.route';

const ENTITY_STATES = [...paisRoute, ...paisPopupRoute];

@NgModule({
  imports: [LastcrmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PaisComponent, PaisDetailComponent, PaisUpdateComponent, PaisDeleteDialogComponent, PaisDeletePopupComponent],
  entryComponents: [PaisComponent, PaisUpdateComponent, PaisDeleteDialogComponent, PaisDeletePopupComponent]
})
export class LastcrmPaisModule {}
