import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICita, Cita } from 'app/shared/model/cita.model';
import { CitaService } from './cita.service';
import { IAgente } from 'app/shared/model/agente.model';
import { AgenteService } from 'app/entities/agente/agente.service';

@Component({
  selector: 'jhi-cita-update',
  templateUrl: './cita-update.component.html'
})
export class CitaUpdateComponent implements OnInit {
  isSaving: boolean;

  agentes: IAgente[];

  editForm = this.fb.group({
    id: [],
    fecha: [null, [Validators.required]],
    comentario: [null, [Validators.maxLength(3999)]],
    estado: [null, [Validators.required]],
    agenteId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected citaService: CitaService,
    protected agenteService: AgenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cita }) => {
      this.updateForm(cita);
    });
    this.agenteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgente[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgente[]>) => response.body)
      )
      .subscribe((res: IAgente[]) => (this.agentes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cita: ICita) {
    this.editForm.patchValue({
      id: cita.id,
      fecha: cita.fecha != null ? cita.fecha.format(DATE_TIME_FORMAT) : null,
      comentario: cita.comentario,
      estado: cita.estado,
      agenteId: cita.agenteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cita = this.createFromForm();
    if (cita.id !== undefined) {
      this.subscribeToSaveResponse(this.citaService.update(cita));
    } else {
      this.subscribeToSaveResponse(this.citaService.create(cita));
    }
  }

  private createFromForm(): ICita {
    return {
      ...new Cita(),
      id: this.editForm.get(['id']).value,
      fecha: this.editForm.get(['fecha']).value != null ? moment(this.editForm.get(['fecha']).value, DATE_TIME_FORMAT) : undefined,
      comentario: this.editForm.get(['comentario']).value,
      estado: this.editForm.get(['estado']).value,
      agenteId: this.editForm.get(['agenteId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICita>>) {
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

  trackAgenteById(index: number, item: IAgente) {
    return item.id;
  }
}
