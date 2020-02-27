import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LastcrmSharedModule } from 'app/shared/shared.module';
import { TipoProductoComponent } from './tipo-producto.component';
import { TipoProductoDetailComponent } from './tipo-producto-detail.component';
import { TipoProductoUpdateComponent } from './tipo-producto-update.component';
import { TipoProductoDeletePopupComponent, TipoProductoDeleteDialogComponent } from './tipo-producto-delete-dialog.component';
import { tipoProductoRoute, tipoProductoPopupRoute } from './tipo-producto.route';

const ENTITY_STATES = [...tipoProductoRoute, ...tipoProductoPopupRoute];

@NgModule({
  imports: [LastcrmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TipoProductoComponent,
    TipoProductoDetailComponent,
    TipoProductoUpdateComponent,
    TipoProductoDeleteDialogComponent,
    TipoProductoDeletePopupComponent
  ],
  entryComponents: [TipoProductoComponent, TipoProductoUpdateComponent, TipoProductoDeleteDialogComponent, TipoProductoDeletePopupComponent]
})
export class LastcrmTipoProductoModule {}
