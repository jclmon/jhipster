import { Component, OnInit } from '@angular/core';
import { Provincia } from './provincia.model';
import { ProvinciaService } from './provincia.service';
import { NavController, AlertController } from '@ionic/angular';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'page-provincia-detail',
    templateUrl: 'provincia-detail.html'
})
export class ProvinciaDetailPage implements OnInit {
    provincia: Provincia;

    constructor(
        private navController: NavController,
        private provinciaService: ProvinciaService,
        private activatedRoute: ActivatedRoute,
        private alertController: AlertController
    ) { }

    ngOnInit(): void {
        this.activatedRoute.data.subscribe((response) => {
            this.provincia = response.data;
        });
    }

    open(item: Provincia) {
        this.navController.navigateForward('/tabs/entities/provincia/' + item.id + '/edit');
    }

    async deleteModal(item: Provincia) {
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
                        this.provinciaService.delete(item.id).subscribe(() => {
                            this.navController.navigateForward('/tabs/entities/provincia');
                        });
                    }
                }
            ]
        });
        await alert.present();
    }


}
