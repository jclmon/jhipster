import { Component } from '@angular/core';
import { NavController, ToastController, Platform, IonItemSliding } from '@ionic/angular';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { Provincia } from './provincia.model';
import { ProvinciaService } from './provincia.service';

@Component({
    selector: 'page-provincia',
    templateUrl: 'provincia.html'
})
export class ProvinciaPage {
    provincias: Provincia[];

    // todo: add pagination

    constructor(
        private navController: NavController,
        private provinciaService: ProvinciaService,
        private toastCtrl: ToastController,
        public plt: Platform
    ) {
        this.provincias = [];
    }

    ionViewWillEnter() {
        this.loadAll();
    }

    async loadAll(refresher?) {
        this.provinciaService.query().pipe(
            filter((res: HttpResponse<Provincia[]>) => res.ok),
            map((res: HttpResponse<Provincia[]>) => res.body)
        )
        .subscribe(
            (response: Provincia[]) => {
                this.provincias = response;
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

    trackId(index: number, item: Provincia) {
        return item.id;
    }

    new() {
        this.navController.navigateForward('/tabs/entities/provincia/new');
    }

    edit(item: IonItemSliding, provincia: Provincia) {
        this.navController.navigateForward('/tabs/entities/provincia/' + provincia.id + '/edit');
        item.close();
    }

    async delete(provincia) {
        this.provinciaService.delete(provincia.id).subscribe(async () => {
            const toast = await this.toastCtrl.create(
                {message: 'Provincia deleted successfully.', duration: 3000, position: 'middle'});
            toast.present();
            this.loadAll();
        }, (error) => console.error(error));
    }

    view(provincia: Provincia) {
        this.navController.navigateForward('/tabs/entities/provincia/' + provincia.id + '/view');
    }
}
