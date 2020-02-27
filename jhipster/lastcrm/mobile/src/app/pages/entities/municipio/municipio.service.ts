import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiService } from 'src/app/services/api/api.service';
import { createRequestOption } from 'src/app/shared';
import { Municipio } from './municipio.model';

@Injectable({ providedIn: 'root'})
export class MunicipioService {
    private resourceUrl = ApiService.API_URL + '/municipios';

    constructor(protected http: HttpClient) { }

    create(municipio: Municipio): Observable<HttpResponse<Municipio>> {
        return this.http.post<Municipio>(this.resourceUrl, municipio, { observe: 'response'});
    }

    update(municipio: Municipio): Observable<HttpResponse<Municipio>> {
        return this.http.put(this.resourceUrl, municipio, { observe: 'response'});
    }

    find(id: number): Observable<HttpResponse<Municipio>> {
        return this.http.get(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    query(req?: any): Observable<HttpResponse<Municipio[]>> {
        const options = createRequestOption(req);
        return this.http.get<Municipio[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }
}
