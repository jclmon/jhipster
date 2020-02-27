import { Component } from '@angular/core';
import { NavController, ToastController, Platform, IonItemSliding } from '@ionic/angular';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { JhiDataUtils } from 'ng-jhipster';
import { Producto } from './producto.model';
import { ProductoService } from './producto.service';

@Component({
    selector: 'page-producto',
    templateUrl: 'producto.html'
})
export class ProductoPage {
    productos: Producto[];

    // todo: add pagination

    constructor(
        private dataUtils: JhiDataUtils,
        private navController: NavController,
        private productoService: ProductoService,
        private toastCtrl: ToastController,
        public plt: Platform
    ) {
        this.productos = [];
    }

    ionViewWillEnter() {
        this.loadAll();
    }

    async loadAll(refresher?) {
        this.productoService.query().pipe(
            filter((res: HttpResponse<Producto[]>) => res.ok),
            map((res: HttpResponse<Producto[]>) => res.body)
        )
        .subscribe(
            (response: Producto[]) => {
                this.productos = response;
                if (typeof(refresher) !== 'undefined') {
                    setTimeout(() => {
                        refresher.target.complete();
                    }, 750);
                }
            },
            async (error) => {
                console.error(error);
                const toast = await this.toastCtrl.create({message: 'Failed to load data', duration: 2000, position: 'middle'});
                toast.present();
            });
    }

    trackId(index: number, item: Producto) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    new() {
        this.navController.navigateForward('/tabs/entities/producto/new');
    }

    edit(item: IonItemSliding, producto: Producto) {
        this.navController.navigateForward('/tabs/entities/producto/' + producto.id + '/edit');
        item.close();
    }

    async delete(producto) {
        this.productoService.delete(producto.id).subscribe(async () => {
            const toast = await this.toastCtrl.create(
                {message: 'Producto deleted successfully.', duration: 3000, position: 'middle'});
            toast.present();
            this.loadAll();
        }, (error) => console.error(error));
    }

    view(producto: Producto) {
        this.navController.navigateForward('/tabs/entities/producto/' + producto.id + '/view');
    }
}
