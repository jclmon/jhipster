import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NavController, Platform, ToastController } from '@ionic/angular';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Municipio } from './municipio.model';
import { MunicipioService } from './municipio.service';
import { Provincia, ProvinciaService } from '../provincia';

@Component({
    selector: 'page-municipio-update',
    templateUrl: 'municipio-update.html'
})
export class MunicipioUpdatePage implements OnInit {

    municipio: Municipio;
    provincias: Provincia[];
    isSaving = false;
    isNew = true;
    isReadyToSave: boolean;

    form = this.formBuilder.group({
        id: [],
        nombre: [null, [Validators.required]],
        codPostal: [null, [Validators.required]],
          provinciaId: [null, [Validators.required]],
    });

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected navController: NavController,
        protected formBuilder: FormBuilder,
        protected platform: Platform,
        protected toastCtrl: ToastController,
        private provinciaService: ProvinciaService,
        private municipioService: MunicipioService
    ) {

        // Watch the form for changes, and
        this.form.valueChanges.subscribe((v) => {
            this.isReadyToSave = this.form.valid;
        });

    }

    ngOnInit() {
        this.provinciaService.query()
            .subscribe(data => { this.provincias = data.body; }, (error) => this.onError(error));
        this.activatedRoute.data.subscribe((response) => {
            this.updateForm(response.data);
            this.municipio = response.data;
            this.isNew = this.municipio.id === null || this.municipio.id === undefined;
        });
    }

    updateForm(municipio: Municipio) {
        this.form.patchValue({
            id: municipio.id,
            nombre: municipio.nombre,
            codPostal: municipio.codPostal,
            provinciaId: municipio.provinciaId,
        });
    }

    save() {
        this.isSaving = true;
        const municipio = this.createFromForm();
        if (!this.isNew) {
            this.subscribeToSaveResponse(this.municipioService.update(municipio));
        } else {
            this.subscribeToSaveResponse(this.municipioService.create(municipio));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<Municipio>>) {
        result.subscribe((res: HttpResponse<Municipio>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onError(res.error));
    }

    async onSaveSuccess(response) {
        let action = 'updated';
        if (response.status === 201) {
          action = 'created';
        }
        this.isSaving = false;
        const toast = await this.toastCtrl.create({message: `Municipio ${action} successfully.`, duration: 2000, position: 'middle'});
        toast.present();
        this.navController.navigateBack('/tabs/entities/municipio');
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

    private createFromForm(): Municipio {
        return {
            ...new Municipio(),
            id: this.form.get(['id']).value,
            nombre: this.form.get(['nombre']).value,
            codPostal: this.form.get(['codPostal']).value,
            provinciaId: this.form.get(['provinciaId']).value,
        };
    }

    compareProvincia(first: Provincia, second: Provincia): boolean {
        return first && second ? first.id === second.id : first === second;
    }

    trackProvinciaById(index: number, item: Provincia) {
        return item.id;
    }
}
