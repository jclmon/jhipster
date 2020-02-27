import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiService } from 'src/app/services/api/api.service';
import { createRequestOption } from 'src/app/shared';
import { Provincia } from './provincia.model';

@Injectable({ providedIn: 'root'})
export class ProvinciaService {
    private resourceUrl = ApiService.API_URL + '/provincias';

    constructor(protected http: HttpClient) { }

    create(provincia: Provincia): Observable<HttpResponse<Provincia>> {
        return this.http.post<Provincia>(this.resourceUrl, provincia, { observe: 'response'});
    }

    update(provincia: Provincia): Observable<HttpResponse<Provincia>> {
        return this.http.put(this.resourceUrl, provincia, { observe: 'response'});
    }

    find(id: number): Observable<HttpResponse<Provincia>> {
        return this.http.get(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    query(req?: any): Observable<HttpResponse<Provincia[]>> {
        const options = createRequestOption(req);
        return this.http.get<Provincia[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }
}
