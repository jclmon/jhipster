import { Component } from '@angular/core';
import { NavController, ToastController, Platform, IonItemSliding } from '@ionic/angular';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { Municipio } from './municipio.model';
import { MunicipioService } from './municipio.service';

@Component({
    selector: 'page-municipio',
    templateUrl: 'municipio.html'
})
export class MunicipioPage {
    municipios: Municipio[];

    // todo: add pagination

    constructor(
        private navController: NavController,
        private municipioService: MunicipioService,
        private toastCtrl: ToastController,
        public plt: Platform
    ) {
        this.municipios = [];
    }

    ionViewWillEnter() {
        this.loadAll();
    }

    async loadAll(refresher?) {
        this.municipioService.query().pipe(
            filter((res: HttpResponse<Municipio[]>) => res.ok),
            map((res: HttpResponse<Municipio[]>) => res.body)
        )
        .subscribe(
            (response: Municipio[]) => {
                this.municipios = response;
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

    trackId(index: number, item: Municipio) {
        return item.id;
    }

    new() {
        this.navController.navigateForward('/tabs/entities/municipio/new');
    }

    edit(item: IonItemSliding, municipio: Municipio) {
        this.navController.navigateForward('/tabs/entities/municipio/' + municipio.id + '/edit');
        item.close();
    }

    async delete(municipio) {
        this.municipioService.delete(municipio.id).subscribe(async () => {
            const toast = await this.toastCtrl.create(
                {message: 'Municipio deleted successfully.', duration: 3000, position: 'middle'});
            toast.present();
            this.loadAll();
        }, (error) => console.error(error));
    }

    view(municipio: Municipio) {
        this.navController.navigateForward('/tabs/entities/municipio/' + municipio.id + '/view');
    }
}
