import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFuente } from 'app/shared/model/fuente.model';

type EntityResponseType = HttpResponse<IFuente>;
type EntityArrayResponseType = HttpResponse<IFuente[]>;

@Injectable({ providedIn: 'root' })
export class FuenteService {
  public resourceUrl = SERVER_API_URL + 'api/fuentes';

  constructor(protected http: HttpClient) {}

  create(fuente: IFuente): Observable<EntityResponseType> {
    return this.http.post<IFuente>(this.resourceUrl, fuente, { observe: 'response' });
  }

  update(fuente: IFuente): Observable<EntityResponseType> {
    return this.http.put<IFuente>(this.resourceUrl, fuente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
