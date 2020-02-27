import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LastcrmTestModule } from '../../../test.module';
import { FichaClienteUpdateComponent } from 'app/entities/ficha-cliente/ficha-cliente-update.component';
import { FichaClienteService } from 'app/entities/ficha-cliente/ficha-cliente.service';
import { FichaCliente } from 'app/shared/model/ficha-cliente.model';

describe('Component Tests', () => {
  describe('FichaCliente Management Update Component', () => {
    let comp: FichaClienteUpdateComponent;
    let fixture: ComponentFixture<FichaClienteUpdateComponent>;
    let service: FichaClienteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LastcrmTestModule],
        declarations: [FichaClienteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FichaClienteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FichaClienteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FichaClienteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FichaCliente(123);
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
        const entity = new FichaCliente();
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
