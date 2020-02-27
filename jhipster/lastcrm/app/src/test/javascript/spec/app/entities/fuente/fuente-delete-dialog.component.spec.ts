import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LastcrmTestModule } from '../../../test.module';
import { FuenteDeleteDialogComponent } from 'app/entities/fuente/fuente-delete-dialog.component';
import { FuenteService } from 'app/entities/fuente/fuente.service';

describe('Component Tests', () => {
  describe('Fuente Management Delete Component', () => {
    let comp: FuenteDeleteDialogComponent;
    let fixture: ComponentFixture<FuenteDeleteDialogComponent>;
    let service: FuenteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LastcrmTestModule],
        declarations: [FuenteDeleteDialogComponent]
      })
        .overrideTemplate(FuenteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FuenteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FuenteService);
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
