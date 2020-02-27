import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICita } from 'app/shared/model/cita.model';
import { CitaService } from './cita.service';

@Component({
  selector: 'jhi-cita-delete-dialog',
  templateUrl: './cita-delete-dialog.component.html'
})
export class CitaDeleteDialogComponent {
  cita: ICita;

  constructor(protected citaService: CitaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.citaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'citaListModification',
        content: 'Deleted an cita'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cita-delete-popup',
  template: ''
})
export class CitaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cita }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CitaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cita = cita;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/cita', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/cita', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
