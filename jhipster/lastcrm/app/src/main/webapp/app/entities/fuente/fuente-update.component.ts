import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFuente, Fuente } from 'app/shared/model/fuente.model';
import { FuenteService } from './fuente.service';

@Component({
  selector: 'jhi-fuente-update',
  templateUrl: './fuente-update.component.html'
})
export class FuenteUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(200)]]
  });

  constructor(protected fuenteService: FuenteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fuente }) => {
      this.updateForm(fuente);
    });
  }

  updateForm(fuente: IFuente) {
    this.editForm.patchValue({
      id: fuente.id,
      nombre: fuente.nombre
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fuente = this.createFromForm();
    if (fuente.id !== undefined) {
      this.subscribeToSaveResponse(this.fuenteService.update(fuente));
    } else {
      this.subscribeToSaveResponse(this.fuenteService.create(fuente));
    }
  }

  private createFromForm(): IFuente {
    return {
      ...new Fuente(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuente>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
