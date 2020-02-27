import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LastcrmTestModule } from '../../../test.module';
import { FichaClienteDetailComponent } from 'app/entities/ficha-cliente/ficha-cliente-detail.component';
import { FichaCliente } from 'app/shared/model/ficha-cliente.model';

describe('Component Tests', () => {
  describe('FichaCliente Management Detail Component', () => {
    let comp: FichaClienteDetailComponent;
    let fixture: ComponentFixture<FichaClienteDetailComponent>;
    const route = ({ data: of({ fichaCliente: new FichaCliente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LastcrmTestModule],
        declarations: [FichaClienteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FichaClienteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FichaClienteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fichaCliente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
