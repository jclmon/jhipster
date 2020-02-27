import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LastcrmSharedModule } from 'app/shared/shared.module';
import { CitaComponent } from './cita.component';
import { CitaDetailComponent } from './cita-detail.component';
import { CitaUpdateComponent } from './cita-update.component';
import { CitaDeletePopupComponent, CitaDeleteDialogComponent } from './cita-delete-dialog.component';
import { citaRoute, citaPopupRoute } from './cita.route';

const ENTITY_STATES = [...citaRoute, ...citaPopupRoute];

@NgModule({
  imports: [LastcrmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CitaComponent, CitaDetailComponent, CitaUpdateComponent, CitaDeleteDialogComponent, CitaDeletePopupComponent],
  entryComponents: [CitaComponent, CitaUpdateComponent, CitaDeleteDialogComponent, CitaDeletePopupComponent]
})
export class LastcrmCitaModule {}
