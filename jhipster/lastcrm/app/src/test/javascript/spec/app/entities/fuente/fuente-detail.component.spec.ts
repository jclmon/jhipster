import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LastcrmTestModule } from '../../../test.module';
import { FuenteDetailComponent } from 'app/entities/fuente/fuente-detail.component';
import { Fuente } from 'app/shared/model/fuente.model';

describe('Component Tests', () => {
  describe('Fuente Management Detail Component', () => {
    let comp: FuenteDetailComponent;
    let fixture: ComponentFixture<FuenteDetailComponent>;
    const route = ({ data: of({ fuente: new Fuente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LastcrmTestModule],
        declarations: [FuenteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FuenteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FuenteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fuente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
