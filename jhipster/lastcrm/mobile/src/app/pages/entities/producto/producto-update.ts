import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { JhiDataUtils } from 'ng-jhipster';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { FormBuilder, Validators } from '@angular/forms';
import { NavController, Platform, ToastController } from '@ionic/angular';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Producto } from './producto.model';
import { ProductoService } from './producto.service';
import { Municipio, MunicipioService } from '../municipio';
import { TipoProducto, TipoProductoService } from '../tipo-producto';

@Component({
    selector: 'page-producto-update',
    templateUrl: 'producto-update.html'
})
export class ProductoUpdatePage implements OnInit {

    producto: Producto;
    municipios: Municipio[];
    tipoProductos: TipoProducto[];
    @ViewChild('fileInput',{static: false}) fileInput;
    cameraOptions: CameraOptions;
    isSaving = false;
    isNew = true;
    isReadyToSave: boolean;

    form = this.formBuilder.group({
        id: [],
        direccion: [null, [Validators.required]],
        comentario: [null, []],
        destino: [null, [Validators.required]],
        precio: [null, []],
        image1: [null, []],
        image1ContentType: [null, []],
        image2: [null, []],
        image2ContentType: [null, []],
        image3: [null, []],
        image3ContentType: [null, []],
        image4: [null, []],
        image4ContentType: [null, []],
        image5: [null, []],
        image5ContentType: [null, []],
        municipioId: [null, [Validators.required]],
        tipoProductoId: [null, [Validators.required]],
    });

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected navController: NavController,
        protected formBuilder: FormBuilder,
        protected platform: Platform,
        protected toastCtrl: ToastController,
        private dataUtils: JhiDataUtils,

        private elementRef: ElementRef,
        private camera: Camera,
        private municipioService: MunicipioService,
        private tipoProductoService: TipoProductoService,
        private productoService: ProductoService
    ) {

        // Watch the form for changes, and
        this.form.valueChanges.subscribe((v) => {
            this.isReadyToSave = this.form.valid;
        });

        // Set the Camera options
        this.cameraOptions = {
            quality: 100,
            targetWidth: 900,
            targetHeight: 600,
            destinationType: this.camera.DestinationType.DATA_URL,
            encodingType: this.camera.EncodingType.JPEG,
            mediaType: this.camera.MediaType.PICTURE,
            saveToPhotoAlbum: false,
            allowEdit: true,
            sourceType: 1
        };
    }

    ngOnInit() {
        this.municipioService.query()
            .subscribe(data => { this.municipios = data.body; }, (error) => this.onError(error));
        this.tipoProductoService.query()
            .subscribe(data => { this.tipoProductos = data.body; }, (error) => this.onError(error));
        this.activatedRoute.data.subscribe((response) => {
            this.updateForm(response.data);
            this.producto = response.data;
            this.isNew = this.producto.id === null || this.producto.id === undefined;
        });
    }

    updateForm(producto: Producto) {
        this.form.patchValue({
            id: producto.id,
            direccion: producto.direccion,
            comentario: producto.comentario,
            destino: producto.destino,
            precio: producto.precio,
            image1: producto.image1,
            image1ContentType: producto.image1ContentType,
            image2: producto.image2,
            image2ContentType: producto.image2ContentType,
            image3: producto.image3,
            image3ContentType: producto.image3ContentType,
            image4: producto.image4,
            image4ContentType: producto.image4ContentType,
            image5: producto.image5,
            image5ContentType: producto.image5ContentType,
            municipioId: producto.municipioId,
            tipoProductoId: producto.tipoProductoId,
        });
    }

    save() {
        this.isSaving = true;
        const producto = this.createFromForm();
        if (!this.isNew) {
            this.subscribeToSaveResponse(this.productoService.update(producto));
        } else {
            this.subscribeToSaveResponse(this.productoService.create(producto));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<Producto>>) {
        result.subscribe((res: HttpResponse<Producto>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onError(res.error));
    }

    async onSaveSuccess(response) {
        let action = 'updated';
        if (response.status === 201) {
          action = 'created';
        }
        this.isSaving = false;
        const toast = await this.toastCtrl.create({message: `Producto ${action} successfully.`, duration: 2000, position: 'middle'});
        toast.present();
        this.navController.navigateBack('/tabs/entities/producto');
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

    private createFromForm(): Producto {
        return {
            ...new Producto(),
            id: this.form.get(['id']).value,
            direccion: this.form.get(['direccion']).value,
            comentario: this.form.get(['comentario']).value,
            destino: this.form.get(['destino']).value,
            precio: this.form.get(['precio']).value,
            image1: this.form.get(['image1']).value,
            image1ContentType: this.form.get(['image1ContentType']).value,
            image2: this.form.get(['image2']).value,
            image2ContentType: this.form.get(['image2ContentType']).value,
            image3: this.form.get(['image3']).value,
            image3ContentType: this.form.get(['image3ContentType']).value,
            image4: this.form.get(['image4']).value,
            image4ContentType: this.form.get(['image4ContentType']).value,
            image5: this.form.get(['image5']).value,
            image5ContentType: this.form.get(['image5ContentType']).value,
            municipioId: this.form.get(['municipioId']).value,
            tipoProductoId: this.form.get(['tipoProductoId']).value,
        };
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
        this.processWebImage(event, field);
    }

    getPicture(fieldName) {
        if (Camera.installed()) {
            this.camera.getPicture(this.cameraOptions).then((data) => {
                this.producto[fieldName] = data;
                this.producto[fieldName + 'ContentType'] = 'image/jpeg';
                this.form.patchValue({ [fieldName]: data });
                this.form.patchValue({ [fieldName + 'ContentType']: 'image/jpeg' });
            }, (err) => {
                alert('Unable to take photo');
            });
        } else {
            this.fileInput.nativeElement.click();
        }
    }

    processWebImage(event, fieldName) {
        const reader = new FileReader();
        reader.onload = (readerEvent) => {

            let imageData = (readerEvent.target as any).result;
            const imageType = event.target.files[0].type;
            imageData = imageData.substring(imageData.indexOf(',') + 1);

            this.form.patchValue({ [fieldName]: imageData });
            this.form.patchValue({ [fieldName + 'ContentType']: imageType });
        };

        reader.readAsDataURL(event.target.files[0]);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.producto, this.elementRef, field, fieldContentType, idInput);
        this.form.patchValue( {[field]: ''} );
    }
    compareMunicipio(first: Municipio, second: Municipio): boolean {
        return first && second ? first.id === second.id : first === second;
    }

    trackMunicipioById(index: number, item: Municipio) {
        return item.id;
    }
    compareTipoProducto(first: TipoProducto, second: TipoProducto): boolean {
        return first && second ? first.id === second.id : first === second;
    }

    trackTipoProductoById(index: number, item: TipoProducto) {
        return item.id;
    }
}
