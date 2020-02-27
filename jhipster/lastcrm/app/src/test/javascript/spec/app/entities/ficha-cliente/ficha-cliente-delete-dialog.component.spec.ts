import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LastcrmTestModule } from '../../../test.module';
import { FichaClienteDeleteDialogComponent } from 'app/entities/ficha-cliente/ficha-cliente-delete-dialog.component';
import { FichaClienteService } from 'app/entities/ficha-cliente/ficha-cliente.service';

describe('Component Tests', () => {
  describe('FichaCliente Management Delete Component', () => {
    let comp: FichaClienteDeleteDialogComponent;
    let fixture: ComponentFixture<FichaClienteDeleteDialogComponent>;
    let service: FichaClienteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LastcrmTestModule],
        declarations: [FichaClienteDeleteDialogComponent]
      })
        .overrideTemplate(FichaClienteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FichaClienteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FichaClienteService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
