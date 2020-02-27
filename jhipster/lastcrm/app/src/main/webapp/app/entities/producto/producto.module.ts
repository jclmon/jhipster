import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LastcrmSharedModule } from 'app/shared/shared.module';
import { ProductoComponent } from './producto.component';
import { ProductoDetailComponent } from './producto-detail.component';
import { ProductoUpdateComponent } from './producto-update.component';
import { ProductoDeletePopupComponent, ProductoDeleteDialogComponent } from './producto-delete-dialog.component';
import { productoRoute, productoPopupRoute } from './producto.route';

const ENTITY_STATES = [...productoRoute, ...productoPopupRoute];

@NgModule({
  imports: [LastcrmSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductoComponent,
    ProductoDetailComponent,
    ProductoUpdateComponent,
    ProductoDeleteDialogComponent,
    ProductoDeletePopupComponent
  ],
  entryComponents: [ProductoComponent, ProductoUpdateComponent, ProductoDeleteDialogComponent, ProductoDeletePopupComponent]
})
export class LastcrmProductoModule {}
