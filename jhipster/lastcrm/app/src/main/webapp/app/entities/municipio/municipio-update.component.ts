import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMunicipio, Municipio } from 'app/shared/model/municipio.model';
import { MunicipioService } from './municipio.service';
import { IProvincia } from 'app/shared/model/provincia.model';
import { ProvinciaService } from 'app/entities/provincia/provincia.service';

@Component({
  selector: 'jhi-municipio-update',
  templateUrl: './municipio-update.component.html'
})
export class MunicipioUpdateComponent implements OnInit {
  isSaving: boolean;

  provincias: IProvincia[];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(200)]],
    codPostal: [null, [Validators.required, Validators.maxLength(15)]],
    provinciaId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected municipioService: MunicipioService,
    protected provinciaService: ProvinciaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ municipio }) => {
      this.updateForm(municipio);
    });
    this.provinciaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProvincia[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProvincia[]>) => response.body)
      )
      .subscribe((res: IProvincia[]) => (this.provincias = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(municipio: IMunicipio) {
    this.editForm.patchValue({
      id: municipio.id,
      nombre: municipio.nombre,
      codPostal: municipio.codPostal,
      provinciaId: municipio.provinciaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const municipio = this.createFromForm();
    if (municipio.id !== undefined) {
      this.subscribeToSaveResponse(this.municipioService.update(municipio));
    } else {
      this.subscribeToSaveResponse(this.municipioService.create(municipio));
    }
  }

  private createFromForm(): IMunicipio {
    return {
      ...new Municipio(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      codPostal: this.editForm.get(['codPostal']).value,
      provinciaId: this.editForm.get(['provinciaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMunicipio>>) {
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

  trackProvinciaById(index: number, item: IProvincia) {
    return item.id;
  }
}
