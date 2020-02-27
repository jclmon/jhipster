import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NavController, Platform, ToastController } from '@ionic/angular';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { TipoProducto } from './tipo-producto.model';
import { TipoProductoService } from './tipo-producto.service';

@Component({
    selector: 'page-tipo-producto-update',
    templateUrl: 'tipo-producto-update.html'
})
export class TipoProductoUpdatePage implements OnInit {

    tipoProducto: TipoProducto;
    isSaving = false;
    isNew = true;
    isReadyToSave: boolean;

    form = this.formBuilder.group({
        id: [],
        nombre: [null, [Validators.required]],
    });

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected navController: NavController,
        protected formBuilder: FormBuilder,
        protected platform: Platform,
        protected toastCtrl: ToastController,
        private tipoProductoService: TipoProductoService
    ) {

        // Watch the form for changes, and
        this.form.valueChanges.subscribe((v) => {
            this.isReadyToSave = this.form.valid;
        });

    }

    ngOnInit() {
        this.activatedRoute.data.subscribe((response) => {
            this.updateForm(response.data);
            this.tipoProducto = response.data;
            this.isNew = this.tipoProducto.id === null || this.tipoProducto.id === undefined;
        });
    }

    updateForm(tipoProducto: TipoProducto) {
        this.form.patchValue({
            id: tipoProducto.id,
            nombre: tipoProducto.nombre,
        });
    }

    save() {
        this.isSaving = true;
        const tipoProducto = this.createFromForm();
        if (!this.isNew) {
            this.subscribeToSaveResponse(this.tipoProductoService.update(tipoProducto));
        } else {
            this.subscribeToSaveResponse(this.tipoProductoService.create(tipoProducto));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<TipoProducto>>) {
        result.subscribe((res: HttpResponse<TipoProducto>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onError(res.error));
    }

    async onSaveSuccess(response) {
        let action = 'updated';
        if (response.status === 201) {
          action = 'created';
        }
        this.isSaving = false;
        const toast = await this.toastCtrl.create({message: `TipoProducto ${action} successfully.`, duration: 2000, position: 'middle'});
        toast.present();
        this.navController.navigateBack('/tabs/entities/tipo-producto');
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

    private createFromForm(): TipoProducto {
        return {
            ...new TipoProducto(),
            id: this.form.get(['id']).value,
            nombre: this.form.get(['nombre']).value,
        };
    }

}
