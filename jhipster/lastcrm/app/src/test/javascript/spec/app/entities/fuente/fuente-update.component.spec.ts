import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LastcrmTestModule } from '../../../test.module';
import { FuenteUpdateComponent } from 'app/entities/fuente/fuente-update.component';
import { FuenteService } from 'app/entities/fuente/fuente.service';
import { Fuente } from 'app/shared/model/fuente.model';

describe('Component Tests', () => {
  describe('Fuente Management Update Component', () => {
    let comp: FuenteUpdateComponent;
    let fixture: ComponentFixture<FuenteUpdateComponent>;
    let service: FuenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LastcrmTestModule],
        declarations: [FuenteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FuenteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FuenteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FuenteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Fuente(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Fuente();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
