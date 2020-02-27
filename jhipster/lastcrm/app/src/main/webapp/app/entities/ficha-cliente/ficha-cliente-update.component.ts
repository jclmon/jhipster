import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFichaCliente, FichaCliente } from 'app/shared/model/ficha-cliente.model';
import { FichaClienteService } from './ficha-cliente.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';
import { IArea } from 'app/shared/model/area.model';
import { AreaService } from 'app/entities/area/area.service';
import { ICita } from 'app/shared/model/cita.model';
import { CitaService } from 'app/entities/cita/cita.service';
import { ITipoProducto } from 'app/shared/model/tipo-producto.model';
import { TipoProductoService } from 'app/entities/tipo-producto/tipo-producto.service';

@Component({
  selector: 'jhi-ficha-cliente-update',
  templateUrl: './ficha-cliente-update.component.html'
})
export class FichaClienteUpdateComponent implements OnInit {
  isSaving: boolean;

  clientes: ICliente[];

  productos: IProducto[];

  areas: IArea[];

  citas: ICita[];

  tipoproductos: ITipoProducto[];

  editForm = this.fb.group({
    id: [],
    solicitud: [null, [Validators.required]],
    prioridad: [null, [Validators.required]],
    comentario: [null, [Validators.maxLength(3999)]],
    clienteId: [null, Validators.required],
    productoId: [],
    areas: [null, Validators.required],
    citas: [null, Validators.required],
    tipoProductos: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected fichaClienteService: FichaClienteService,
    protected clienteService: ClienteService,
    protected productoService: ProductoService,
    protected areaService: AreaService,
    protected citaService: CitaService,
    protected tipoProductoService: TipoProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fichaCliente }) => {
      this.updateForm(fichaCliente);
    });
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.areaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IArea[]>) => mayBeOk.ok),
        map((response: HttpResponse<IArea[]>) => response.body)
      )
      .subscribe((res: IArea[]) => (this.areas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.citaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICita[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICita[]>) => response.body)
      )
      .subscribe((res: ICita[]) => (this.citas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipoProductoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoProducto[]>) => response.body)
      )
      .subscribe((res: ITipoProducto[]) => (this.tipoproductos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(fichaCliente: IFichaCliente) {
    this.editForm.patchValue({
      id: fichaCliente.id,
      solicitud: fichaCliente.solicitud,
      prioridad: fichaCliente.prioridad,
      comentario: fichaCliente.comentario,
      clienteId: fichaCliente.clienteId,
      productoId: fichaCliente.productoId,
      areas: fichaCliente.areas,
      citas: fichaCliente.citas,
      tipoProductos: fichaCliente.tipoProductos
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fichaCliente = this.createFromForm();
    if (fichaCliente.id !== undefined) {
      this.subscribeToSaveResponse(this.fichaClienteService.update(fichaCliente));
    } else {
      this.subscribeToSaveResponse(this.fichaClienteService.create(fichaCliente));
    }
  }

  private createFromForm(): IFichaCliente {
    return {
      ...new FichaCliente(),
      id: this.editForm.get(['id']).value,
      solicitud: this.editForm.get(['solicitud']).value,
      prioridad: this.editForm.get(['prioridad']).value,
      comentario: this.editForm.get(['comentario']).value,
      clienteId: this.editForm.get(['clienteId']).value,
      productoId: this.editForm.get(['productoId']).value,
      areas: this.editForm.get(['areas']).value,
      citas: this.editForm.get(['citas']).value,
      tipoProductos: this.editForm.get(['tipoProductos']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFichaCliente>>) {
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

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackAreaById(index: number, item: IArea) {
    return item.id;
  }

  trackCitaById(index: number, item: ICita) {
    return item.id;
  }

  trackTipoProductoById(index: number, item: ITipoProducto) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
