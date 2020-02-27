import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LastcrmSharedModule } from 'app/shared/shared.module';
import { AgenteComponent } from './agente.component';
import { AgenteDetailComponent } from './agente-detail.component';
import { AgenteUpdateComponent } from './agente-update.component';
import { AgenteDeletePopupComponent, AgenteDeleteDialogComponent } from './agente-delete-dialog.component';
import { agenteRoute, agentePopupRoute } from './agente.route';

const ENTITY_STATES = [...agenteRoute, ...agentePopupRoute];

@NgModule({
  imports: [LastcrmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [AgenteComponent, AgenteDetailComponent, AgenteUpdateComponent, AgenteDeleteDialogComponent, AgenteDeletePopupComponent],
  entryComponents: [AgenteComponent, AgenteUpdateComponent, AgenteDeleteDialogComponent, AgenteDeletePopupComponent]
})
export class LastcrmAgenteModule {}
