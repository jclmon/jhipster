import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiService } from 'src/app/services/api/api.service';
import { createRequestOption } from 'src/app/shared';
import { Producto } from './producto.model';

@Injectable({ providedIn: 'root'})
export class ProductoService {
    private resourceUrl = ApiService.API_URL + '/productos';

    constructor(protected http: HttpClient) { }

    create(producto: Producto): Observable<HttpResponse<Producto>> {
        return this.http.post<Producto>(this.resourceUrl, producto, { observe: 'response'});
    }

    update(producto: Producto): Observable<HttpResponse<Producto>> {
        return this.http.put(this.resourceUrl, producto, { observe: 'response'});
    }

    find(id: number): Observable<HttpResponse<Producto>> {
        return this.http.get(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    query(req?: any): Observable<HttpResponse<Producto[]>> {
        const options = createRequestOption(req);
        return this.http.get<Producto[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }
}
