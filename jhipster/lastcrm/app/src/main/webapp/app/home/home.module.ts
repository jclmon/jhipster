import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LastcrmSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTES } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [LastcrmSharedModule, RouterModule.forChild(HOME_ROUTES)],
  declarations: [HomeComponent]
})
export class LastcrmHomeModule {}
