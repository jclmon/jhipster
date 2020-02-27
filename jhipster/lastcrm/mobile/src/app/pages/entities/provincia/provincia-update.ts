import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NavController, Platform, ToastController } from '@ionic/angular';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Provincia } from './provincia.model';
import { ProvinciaService } from './provincia.service';
import { Pais, PaisService } from '../pais';

@Component({
    selector: 'page-provincia-update',
    templateUrl: 'provincia-update.html'
})
export class ProvinciaUpdatePage implements OnInit {

    provincia: Provincia;
    pais: Pais[];
    isSaving = false;
    isNew = true;
    isReadyToSave: boolean;

    form = this.formBuilder.group({
        id: [],
        nombre: [null, [Validators.required]],
          paisId: [null, [Validators.required]],
    });

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected navController: NavController,
        protected formBuilder: FormBuilder,
        protected platform: Platform,
        protected toastCtrl: ToastController,
        private paisService: PaisService,
        private provinciaService: ProvinciaService
    ) {

        // Watch the form for changes, and
        this.form.valueChanges.subscribe((v) => {
            this.isReadyToSave = this.form.valid;
        });

    }

    ngOnInit() {
        this.paisService.query()
            .subscribe(data => { this.pais = data.body; }, (error) => this.onError(error));
        this.activatedRoute.data.subscribe((response) => {
            this.updateForm(response.data);
            this.provincia = response.data;
            this.isNew = this.provincia.id === null || this.provincia.id === undefined;
        });
    }

    updateForm(provincia: Provincia) {
        this.form.patchValue({
            id: provincia.id,
            nombre: provincia.nombre,
            paisId: provincia.paisId,
        });
    }

    save() {
        this.isSaving = true;
        const provincia = this.createFromForm();
        if (!this.isNew) {
            this.subscribeToSaveResponse(this.provinciaService.update(provincia));
        } else {
            this.subscribeToSaveResponse(this.provinciaService.create(provincia));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<Provincia>>) {
        result.subscribe((res: HttpResponse<Provincia>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onError(res.error));
    }

    async onSaveSuccess(response) {
        let action = 'updated';
        if (response.status === 201) {
          action = 'created';
        }
        this.isSaving = false;
        const toast = await this.toastCtrl.create({message: `Provincia ${action} successfully.`, duration: 2000, position: 'middle'});
        toast.present();
        this.navController.navigateBack('/tabs/entities/provincia');
    }

    previousState() {
        window.history.back();
    }

    async onError(error) {
        this.isSaving = false;
        console.error(error);
        const toast = await this.toastCtrl.create({message: 'Failed to load data', duration: 2000, position: 'middle'});
        toast.present();
    }

    private createFromForm(): Provincia {
        return {
            ...new Provincia(),
            id: this.form.get(['id']).value,
            nombre: this.form.get(['nombre']).value,
            paisId: this.form.get(['paisId']).value,
        };
    }

    comparePais(first: Pais, second: Pais): boolean {
        return first && second ? first.id === second.id : first === second;
    }

    trackPaisById(index: number, item: Pais) {
        return item.id;
    }
}
