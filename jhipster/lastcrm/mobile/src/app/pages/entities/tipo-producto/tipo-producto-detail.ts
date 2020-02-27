import { Component, OnInit } from '@angular/core';
import { TipoProducto } from './tipo-producto.model';
import { TipoProductoService } from './tipo-producto.service';
import { NavController, AlertController } from '@ionic/angular';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'page-tipo-producto-detail',
    templateUrl: 'tipo-producto-detail.html'
})
export class TipoProductoDetailPage implements OnInit {
    tipoProducto: TipoProducto;

    constructor(
        private navController: NavController,
        private tipoProductoService: TipoProductoService,
        private activatedRoute: ActivatedRoute,
        private alertController: AlertController
    ) { }

    ngOnInit(): void {
        this.activatedRoute.data.subscribe((response) => {
            this.tipoProducto = response.data;
        });
    }

    open(item: TipoProducto) {
        this.navController.navigateForward('/tabs/entities/tipo-producto/' + item.id + '/edit');
    }

    async deleteModal(item: TipoProducto) {
        const alert = await this.alertController.create({
            header: 'Confirm the deletion?',
            buttons: [
                {
                    text: 'Cancel',
                    role: 'cancel',
                    cssClass: 'secondary'
                }, {
                    text: 'Delete',
                    handler: () => {
                        this.tipoProductoService.delete(item.id).subscribe(() => {
                            this.navController.navigateForward('/tabs/entities/tipo-producto');
                        });
                    }
                }
            ]
        });
        await alert.present();
    }


}
