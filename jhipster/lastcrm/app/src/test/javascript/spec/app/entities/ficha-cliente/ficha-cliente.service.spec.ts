import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { FichaClienteService } from 'app/entities/ficha-cliente/ficha-cliente.service';
import { IFichaCliente, FichaCliente } from 'app/shared/model/ficha-cliente.model';
import { Solicitud } from 'app/shared/model/enumerations/solicitud.model';
import { Prioridad } from 'app/shared/model/enumerations/prioridad.model';

describe('Service Tests', () => {
  describe('FichaCliente Service', () => {
    let injector: TestBed;
    let service: FichaClienteService;
    let httpMock: HttpTestingController;
    let elemDefault: IFichaCliente;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(FichaClienteService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new FichaCliente(0, Solicitud.COMPRA, Prioridad.ALTA, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a FichaCliente', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new FichaCliente(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a FichaCliente', () => {
        const returnedFromService = Object.assign(
          {
            solicitud: 'BBBBBB',
            prioridad: 'BBBBBB',
            comentario: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of FichaCliente', () => {
        const returnedFromService = Object.assign(
          {
            solicitud: 'BBBBBB',
            prioridad: 'BBBBBB',
            comentario: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FichaCliente', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
