import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFichaCliente } from 'app/shared/model/ficha-cliente.model';

type EntityResponseType = HttpResponse<IFichaCliente>;
type EntityArrayResponseType = HttpResponse<IFichaCliente[]>;

@Injectable({ providedIn: 'root' })
export class FichaClienteService {
  public resourceUrl = SERVER_API_URL + 'api/ficha-clientes';

  constructor(protected http: HttpClient) {}

  create(fichaCliente: IFichaCliente): Observable<EntityResponseType> {
    return this.http.post<IFichaCliente>(this.resourceUrl, fichaCliente, { observe: 'response' });
  }

  update(fichaCliente: IFichaCliente): Observable<EntityResponseType> {
    return this.http.put<IFichaCliente>(this.resourceUrl, fichaCliente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFichaCliente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFichaCliente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
