import { Component } from '@angular/core';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-entities',
  templateUrl: 'entities.page.html',
  styleUrls: ['entities.page.scss']
})
export class EntitiesPage {
  entities: Array<any> = [
    {name: 'TipoProducto', component: 'TipoProductoPage', route: 'tipo-producto'},
    {name: 'Pais', component: 'PaisPage', route: 'pais'},
    {name: 'Producto', component: 'ProductoPage', route: 'producto'},
    {name: 'Municipio', component: 'MunicipioPage', route: 'municipio'},
    {name: 'Provincia', component: 'ProvinciaPage', route: 'provincia'},
    /* jhipster-needle-add-entity-page - JHipster will add entity pages here */
  ];

  constructor(public navController: NavController) {}

  openPage(page) {
    this.navController.navigateForward('/tabs/entities/' + page.route);
  }

}
