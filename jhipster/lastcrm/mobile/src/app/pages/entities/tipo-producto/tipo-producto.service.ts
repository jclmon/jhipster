import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiService } from 'src/app/services/api/api.service';
import { createRequestOption } from 'src/app/shared';
import { TipoProducto } from './tipo-producto.model';

@Injectable({ providedIn: 'root'})
export class TipoProductoService {
    private resourceUrl = ApiService.API_URL + '/tipo-productos';

    constructor(protected http: HttpClient) { }

    create(tipoProducto: TipoProducto): Observable<HttpResponse<TipoProducto>> {
        return this.http.post<TipoProducto>(this.resourceUrl, tipoProducto, { observe: 'response'});
    }

    update(tipoProducto: TipoProducto): Observable<HttpResponse<TipoProducto>> {
        return this.http.put(this.resourceUrl, tipoProducto, { observe: 'response'});
    }

    find(id: number): Observable<HttpResponse<TipoProducto>> {
        return this.http.get(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    query(req?: any): Observable<HttpResponse<TipoProducto[]>> {
        const options = createRequestOption(req);
        return this.http.get<TipoProducto[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }
}
