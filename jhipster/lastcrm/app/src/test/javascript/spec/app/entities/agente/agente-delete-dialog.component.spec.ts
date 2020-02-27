import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LastcrmTestModule } from '../../../test.module';
import { AgenteDeleteDialogComponent } from 'app/entities/agente/agente-delete-dialog.component';
import { AgenteService } from 'app/entities/agente/agente.service';

describe('Component Tests', () => {
  describe('Agente Management Delete Component', () => {
    let comp: AgenteDeleteDialogComponent;
    let fixture: ComponentFixture<AgenteDeleteDialogComponent>;
    let service: AgenteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LastcrmTestModule],
        declarations: [AgenteDeleteDialogComponent]
      })
        .overrideTemplate(AgenteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgenteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgenteService);
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
