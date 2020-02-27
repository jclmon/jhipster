import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IArea, Area } from 'app/shared/model/area.model';
import { AreaService } from './area.service';
import { IMunicipio } from 'app/shared/model/municipio.model';
import { MunicipioService } from 'app/entities/municipio/municipio.service';

@Component({
  selector: 'jhi-area-update',
  templateUrl: './area-update.component.html'
})
export class AreaUpdateComponent implements OnInit {
  isSaving: boolean;

  municipios: IMunicipio[];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(200)]],
    municipioId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected areaService: AreaService,
    protected municipioService: MunicipioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ area }) => {
      this.updateForm(area);
    });
    this.municipioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMunicipio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMunicipio[]>) => response.body)
      )
      .subscribe((res: IMunicipio[]) => (this.municipios = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(area: IArea) {
    this.editForm.patchValue({
      id: area.id,
      nombre: area.nombre,
      municipioId: area.municipioId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const area = this.createFromForm();
    if (area.id !== undefined) {
      this.subscribeToSaveResponse(this.areaService.update(area));
    } else {
      this.subscribeToSaveResponse(this.areaService.create(area));
    }
  }

  private createFromForm(): IArea {
    return {
      ...new Area(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      municipioId: this.editForm.get(['municipioId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArea>>) {
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
}
