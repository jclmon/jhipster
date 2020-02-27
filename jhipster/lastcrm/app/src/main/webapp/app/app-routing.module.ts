import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute } from './layouts/error/error.route';
import { navbarRoute } from './layouts/navbar/navbar.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { AboutComponent } from 'app/about/about.component';
import { AgentComponent } from 'app/agent/agent.component';
import { ContactComponent } from 'app/contact/contact.component';
import { BlogComponent } from 'app/blog/blog.component';
import { ServicesComponent } from 'app/services/services.component';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

// const routes: Routes = [
//   {path: 'home', component: PortfolioComponent},
//   {path: 'about', component: AboutComponent},
//   {path: 'item/:id', component: ItemComponent},
//   {path: 'search/:termino', component: SearchComponent},
//   {path: '**', pathMatch: 'full', redirectTo: 'home'}
// ];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          loadChildren: () => import('./admin/admin.module').then(m => m.LastcrmAdminModule)
        },
        {
          path: 'account',
          loadChildren: () => import('./account/account.module').then(m => m.LastcrmAccountModule)
        },
        {
          path: 'properties',
          loadChildren: () => import('./properties/properties.module').then(m => m.PropertiesModule)
        },
        { path: 'about', component: AboutComponent },
        { path: 'agent', component: AgentComponent },
        { path: 'services', component: ServicesComponent },
        { path: 'blog', component: BlogComponent },
        { path: 'contact', component: ContactComponent },
        ...LAYOUT_ROUTES
      ],
      { enableTracing: DEBUG_INFO_ENABLED, useHash: true }
    )
  ],
  exports: [RouterModule]
})
export class LastcrmAppRoutingModule {}
