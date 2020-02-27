import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LastcrmSharedModule } from 'app/shared/shared.module';
import { MunicipioComponent } from './municipio.component';
import { MunicipioDetailComponent } from './municipio-detail.component';
import { MunicipioUpdateComponent } from './municipio-update.component';
import { MunicipioDeletePopupComponent, MunicipioDeleteDialogComponent } from './municipio-delete-dialog.component';
import { municipioRoute, municipioPopupRoute } from './municipio.route';

const ENTITY_STATES = [...municipioRoute, ...municipioPopupRoute];

@NgModule({
  imports: [LastcrmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MunicipioComponent,
    MunicipioDetailComponent,
    MunicipioUpdateComponent,
    MunicipioDeleteDialogComponent,
    MunicipioDeletePopupComponent
  ],
  entryComponents: [MunicipioComponent, MunicipioUpdateComponent, MunicipioDeleteDialogComponent, MunicipioDeletePopupComponent]
})
export class LastcrmMunicipioModule {}
