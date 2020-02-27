import { Component } from '@angular/core';
import { NavController, ToastController, Platform, IonItemSliding } from '@ionic/angular';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { TipoProducto } from './tipo-producto.model';
import { TipoProductoService } from './tipo-producto.service';

@Component({
    selector: 'page-tipo-producto',
    templateUrl: 'tipo-producto.html'
})
export class TipoProductoPage {
    tipoProductos: TipoProducto[];

    // todo: add pagination

    constructor(
        private navController: NavController,
        private tipoProductoService: TipoProductoService,
        private toastCtrl: ToastController,
        public plt: Platform
    ) {
        this.tipoProductos = [];
    }

    ionViewWillEnter() {
        this.loadAll();
    }

    async loadAll(refresher?) {
        this.tipoProductoService.query().pipe(
            filter((res: HttpResponse<TipoProducto[]>) => res.ok),
            map((res: HttpResponse<TipoProducto[]>) => res.body)
        )
        .subscribe(
            (response: TipoProducto[]) => {
                this.tipoProductos = response;
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

    trackId(index: number, item: TipoProducto) {
        return item.id;
    }

    new() {
        this.navController.navigateForward('/tabs/entities/tipo-producto/new');
    }

    edit(item: IonItemSliding, tipoProducto: TipoProducto) {
        this.navController.navigateForward('/tabs/entities/tipo-producto/' + tipoProducto.id + '/edit');
        item.close();
    }

    async delete(tipoProducto) {
        this.tipoProductoService.delete(tipoProducto.id).subscribe(async () => {
            const toast = await this.toastCtrl.create(
                {message: 'TipoProducto deleted successfully.', duration: 3000, position: 'middle'});
            toast.present();
            this.loadAll();
        }, (error) => console.error(error));
    }

    view(tipoProducto: TipoProducto) {
        this.navController.navigateForward('/tabs/entities/tipo-producto/' + tipoProducto.id + '/view');
    }
}
