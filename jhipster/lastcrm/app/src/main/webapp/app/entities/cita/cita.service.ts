import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICita } from 'app/shared/model/cita.model';

type EntityResponseType = HttpResponse<ICita>;
type EntityArrayResponseType = HttpResponse<ICita[]>;

@Injectable({ providedIn: 'root' })
export class CitaService {
  public resourceUrl = SERVER_API_URL + 'api/citas';

  constructor(protected http: HttpClient) {}

  create(cita: ICita): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cita);
    return this.http
      .post<ICita>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cita: ICita): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cita);
    return this.http
      .put<ICita>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICita>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICita[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cita: ICita): ICita {
    const copy: ICita = Object.assign({}, cita, {
      fecha: cita.fecha != null && cita.fecha.isValid() ? cita.fecha.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cita: ICita) => {
        cita.fecha = cita.fecha != null ? moment(cita.fecha) : null;
      });
    }
    return res;
  }
}
