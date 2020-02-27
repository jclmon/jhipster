import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { IFuente } from 'app/shared/model/fuente.model';
import { FuenteService } from 'app/entities/fuente/fuente.service';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html'
})
export class ClienteUpdateComponent implements OnInit {
  isSaving: boolean;

  fuentes: IFuente[];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(200)]],
    apellido1: [null, [Validators.required, Validators.maxLength(200)]],
    apellido2: [null, [Validators.maxLength(200)]],
    nif: [null, [Validators.maxLength(15)]],
    genero: [null, [Validators.required]],
    tlfMovil: [null, [Validators.required, Validators.maxLength(15)]],
    tlfMovil2: [null, [Validators.maxLength(15)]],
    tlfFijo: [null, [Validators.maxLength(15)]],
    fax: [null, [Validators.maxLength(15)]],
    email1: [null, [Validators.required, Validators.pattern('^[^@s]+@[^@s]+.[^@s]+$')]],
    email2: [null, [Validators.pattern('^[^@s]+@[^@s]+.[^@s]+$')]],
    fuenteId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected clienteService: ClienteService,
    protected fuenteService: FuenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);
    });
    this.fuenteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFuente[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFuente[]>) => response.body)
      )
      .subscribe((res: IFuente[]) => (this.fuentes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cliente: ICliente) {
    this.editForm.patchValue({
      id: cliente.id,
      nombre: cliente.nombre,
      apellido1: cliente.apellido1,
      apellido2: cliente.apellido2,
      nif: cliente.nif,
      genero: cliente.genero,
      tlfMovil: cliente.tlfMovil,
      tlfMovil2: cliente.tlfMovil2,
      tlfFijo: cliente.tlfFijo,
      fax: cliente.fax,
      email1: cliente.email1,
      email2: cliente.email2,
      fuenteId: cliente.fuenteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    return {
      ...new Cliente(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      apellido1: this.editForm.get(['apellido1']).value,
      apellido2: this.editForm.get(['apellido2']).value,
      nif: this.editForm.get(['nif']).value,
      genero: this.editForm.get(['genero']).value,
      tlfMovil: this.editForm.get(['tlfMovil']).value,
      tlfMovil2: this.editForm.get(['tlfMovil2']).value,
      tlfFijo: this.editForm.get(['tlfFijo']).value,
      fax: this.editForm.get(['fax']).value,
      email1: this.editForm.get(['email1']).value,
      email2: this.editForm.get(['email2']).value,
      fuenteId: this.editForm.get(['fuenteId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>) {
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

  trackFuenteById(index: number, item: IFuente) {
    return item.id;
  }
}
