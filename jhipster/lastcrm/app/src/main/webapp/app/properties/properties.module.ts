import { NgModule } from '@angular/core';
import { PropertiesComponent } from 'app/properties/properties.component';
import { LastcrmSharedModule } from 'app/shared/shared.module';
import { RouterModule } from '@angular/router';
import { PROPERTIES_ROUTES } from 'app/properties/properties.route';
import { PropertiesSingleComponent } from 'app/properties/properties-single.component';

@NgModule({
  imports: [LastcrmSharedModule, RouterModule.forChild(PROPERTIES_ROUTES)],
  declarations: [PropertiesComponent, PropertiesSingleComponent]
})
export class PropertiesModule {}
