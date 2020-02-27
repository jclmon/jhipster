import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { ProductoService } from 'app/entities/producto/producto.service';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { Solicitud } from 'app/shared/model/enumerations/solicitud.model';

describe('Service Tests', () => {
  describe('Producto Service', () => {
    let injector: TestBed;
    let service: ProductoService;
    let httpMock: HttpTestingController;
    let elemDefault: IProducto;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ProductoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Producto(
        0,
        'AAAAAAA',
        'AAAAAAA',
        Solicitud.COMPRA,
        0,
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        0
      );
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

      it('should create a Producto', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Producto(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Producto', () => {
        const returnedFromService = Object.assign(
          {
            direccion: 'BBBBBB',
            comentario: 'BBBBBB',
            destino: 'BBBBBB',
            precio: 1,
            image1: 'BBBBBB',
            image2: 'BBBBBB',
            image3: 'BBBBBB',
            image4: 'BBBBBB',
            image5: 'BBBBBB',
            precioAnterior: 1,
            dormitorios: 1,
            aseos: 1,
            metros: 1,
            garage: 1,
            anioconstruccion: 1
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

      it('should return a list of Producto', () => {
        const returnedFromService = Object.assign(
          {
            direccion: 'BBBBBB',
            comentario: 'BBBBBB',
            destino: 'BBBBBB',
            precio: 1,
            image1: 'BBBBBB',
            image2: 'BBBBBB',
            image3: 'BBBBBB',
            image4: 'BBBBBB',
            image5: 'BBBBBB',
            precioAnterior: 1,
            dormitorios: 1,
            aseos: 1,
            metros: 1,
            garage: 1,
            anioconstruccion: 1
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

      it('should delete a Producto', () => {
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
