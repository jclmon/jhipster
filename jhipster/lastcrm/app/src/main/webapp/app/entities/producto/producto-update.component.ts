import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { ProductoService } from './producto.service';
import { IMunicipio } from 'app/shared/model/municipio.model';
import { MunicipioService } from 'app/entities/municipio/municipio.service';
import { ITipoProducto } from 'app/shared/model/tipo-producto.model';
import { TipoProductoService } from 'app/entities/tipo-producto/tipo-producto.service';

@Component({
  selector: 'jhi-producto-update',
  templateUrl: './producto-update.component.html'
})
export class ProductoUpdateComponent implements OnInit {
  isSaving: boolean;

  municipios: IMunicipio[];

  tipoproductos: ITipoProducto[];

  editForm = this.fb.group({
    id: [],
    direccion: [null, [Validators.required, Validators.maxLength(500)]],
    comentario: [],
    destino: [null, [Validators.required]],
    precio: [],
    image1: [],
    image1ContentType: [],
    image2: [],
    image2ContentType: [],
    image3: [],
    image3ContentType: [],
    image4: [],
    image4ContentType: [],
    image5: [],
    image5ContentType: [],
    precioAnterior: [],
    dormitorios: [],
    aseos: [],
    metros: [],
    garage: [],
    anioconstruccion: [],
    municipioId: [null, Validators.required],
    tipoProductoId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected productoService: ProductoService,
    protected municipioService: MunicipioService,
    protected tipoProductoService: TipoProductoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.updateForm(producto);
    });
    this.municipioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMunicipio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMunicipio[]>) => response.body)
      )
      .subscribe((res: IMunicipio[]) => (this.municipios = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipoProductoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoProducto[]>) => response.body)
      )
      .subscribe((res: ITipoProducto[]) => (this.tipoproductos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(producto: IProducto) {
    this.editForm.patchValue({
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
      precioAnterior: producto.precioAnterior,
      dormitorios: producto.dormitorios,
      aseos: producto.aseos,
      metros: producto.metros,
      garage: producto.garage,
      anioconstruccion: producto.anioconstruccion,
      municipioId: producto.municipioId,
      tipoProductoId: producto.tipoProductoId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const producto = this.createFromForm();
    if (producto.id !== undefined) {
      this.subscribeToSaveResponse(this.productoService.update(producto));
    } else {
      this.subscribeToSaveResponse(this.productoService.create(producto));
    }
  }

  private createFromForm(): IProducto {
    return {
      ...new Producto(),
      id: this.editForm.get(['id']).value,
      direccion: this.editForm.get(['direccion']).value,
      comentario: this.editForm.get(['comentario']).value,
      destino: this.editForm.get(['destino']).value,
      precio: this.editForm.get(['precio']).value,
      image1ContentType: this.editForm.get(['image1ContentType']).value,
      image1: this.editForm.get(['image1']).value,
      image2ContentType: this.editForm.get(['image2ContentType']).value,
      image2: this.editForm.get(['image2']).value,
      image3ContentType: this.editForm.get(['image3ContentType']).value,
      image3: this.editForm.get(['image3']).value,
      image4ContentType: this.editForm.get(['image4ContentType']).value,
      image4: this.editForm.get(['image4']).value,
      image5ContentType: this.editForm.get(['image5ContentType']).value,
      image5: this.editForm.get(['image5']).value,
      precioAnterior: this.editForm.get(['precioAnterior']).value,
      dormitorios: this.editForm.get(['dormitorios']).value,
      aseos: this.editForm.get(['aseos']).value,
      metros: this.editForm.get(['metros']).value,
      garage: this.editForm.get(['garage']).value,
      anioconstruccion: this.editForm.get(['anioconstruccion']).value,
      municipioId: this.editForm.get(['municipioId']).value,
      tipoProductoId: this.editForm.get(['tipoProductoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackMunicipioById(index: number, item: IMunicipio) {
    return item.id;
  }

  trackTipoProductoById(index: number, item: ITipoProducto) {
    return item.id;
  }
}
