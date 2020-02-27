import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProvincia, Provincia } from 'app/shared/model/provincia.model';
import { ProvinciaService } from './provincia.service';
import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from 'app/entities/pais/pais.service';

@Component({
  selector: 'jhi-provincia-update',
  templateUrl: './provincia-update.component.html'
})
export class ProvinciaUpdateComponent implements OnInit {
  isSaving: boolean;

  pais: IPais[];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(200)]],
    paisId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected provinciaService: ProvinciaService,
    protected paisService: PaisService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ provincia }) => {
      this.updateForm(provincia);
    });
    this.paisService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPais[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPais[]>) => response.body)
      )
      .subscribe((res: IPais[]) => (this.pais = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(provincia: IProvincia) {
    this.editForm.patchValue({
      id: provincia.id,
      nombre: provincia.nombre,
      paisId: provincia.paisId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const provincia = this.createFromForm();
    if (provincia.id !== undefined) {
      this.subscribeToSaveResponse(this.provinciaService.update(provincia));
    } else {
      this.subscribeToSaveResponse(this.provinciaService.create(provincia));
    }
  }

  private createFromForm(): IProvincia {
    return {
      ...new Provincia(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      paisId: this.editForm.get(['paisId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProvincia>>) {
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

  trackPaisById(index: number, item: IPais) {
    return item.id;
  }
}
