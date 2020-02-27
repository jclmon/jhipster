import { Component, OnInit } from '@angular/core';
import { JhiDataUtils } from 'ng-jhipster';
import { Producto } from './producto.model';
import { ProductoService } from './producto.service';
import { NavController, AlertController } from '@ionic/angular';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'page-producto-detail',
    templateUrl: 'producto-detail.html'
})
export class ProductoDetailPage implements OnInit {
    producto: Producto;

    constructor(
        private dataUtils: JhiDataUtils,
        private navController: NavController,
        private productoService: ProductoService,
        private activatedRoute: ActivatedRoute,
        private alertController: AlertController
    ) { }

    ngOnInit(): void {
        this.activatedRoute.data.subscribe((response) => {
            this.producto = response.data;
        });
    }

    open(item: Producto) {
        this.navController.navigateForward('/tabs/entities/producto/' + item.id + '/edit');
    }

    async deleteModal(item: Producto) {
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
                        this.productoService.delete(item.id).subscribe(() => {
                            this.navController.navigateForward('/tabs/entities/producto');
                        });
                    }
                }
            ]
        });
        await alert.present();
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

}
