import { Component, OnInit } from '@angular/core';
import { Municipio } from './municipio.model';
import { MunicipioService } from './municipio.service';
import { NavController, AlertController } from '@ionic/angular';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'page-municipio-detail',
    templateUrl: 'municipio-detail.html'
})
export class MunicipioDetailPage implements OnInit {
    municipio: Municipio;

    constructor(
        private navController: NavController,
        private municipioService: MunicipioService,
        private activatedRoute: ActivatedRoute,
        private alertController: AlertController
    ) { }

    ngOnInit(): void {
        this.activatedRoute.data.subscribe((response) => {
            this.municipio = response.data;
        });
    }

    open(item: Municipio) {
        this.navController.navigateForward('/tabs/entities/municipio/' + item.id + '/edit');
    }

    async deleteModal(item: Municipio) {
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
                        this.municipioService.delete(item.id).subscribe(() => {
                            this.navController.navigateForward('/tabs/entities/municipio');
                        });
                    }
                }
            ]
        });
        await alert.present();
    }


}
