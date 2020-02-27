import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAgente, Agente } from 'app/shared/model/agente.model';
import { AgenteService } from './agente.service';

@Component({
  selector: 'jhi-agente-update',
  templateUrl: './agente-update.component.html'
})
export class AgenteUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required, Validators.maxLength(200)]],
    apellido1: [null, [Validators.required, Validators.maxLength(200)]],
    apellido2: [null, [Validators.maxLength(200)]],
    telefono: [null, [Validators.required, Validators.maxLength(15)]]
  });

  constructor(protected agenteService: AgenteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agente }) => {
      this.updateForm(agente);
    });
  }

  updateForm(agente: IAgente) {
    this.editForm.patchValue({
      id: agente.id,
      nombre: agente.nombre,
      apellido1: agente.apellido1,
      apellido2: agente.apellido2,
      telefono: agente.telefono
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agente = this.createFromForm();
    if (agente.id !== undefined) {
      this.subscribeToSaveResponse(this.agenteService.update(agente));
    } else {
      this.subscribeToSaveResponse(this.agenteService.create(agente));
    }
  }

  private createFromForm(): IAgente {
    return {
      ...new Agente(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      apellido1: this.editForm.get(['apellido1']).value,
      apellido2: this.editForm.get(['apellido2']).value,
      telefono: this.editForm.get(['telefono']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgente>>) {
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
