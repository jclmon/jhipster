import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFuente } from 'app/shared/model/fuente.model';
import { FuenteService } from './fuente.service';

@Component({
  selector: 'jhi-fuente-delete-dialog',
  templateUrl: './fuente-delete-dialog.component.html'
})
export class FuenteDeleteDialogComponent {
  fuente: IFuente;

  constructor(protected fuenteService: FuenteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fuenteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fuenteListModification',
        content: 'Deleted an fuente'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-fuente-delete-popup',
  template: ''
})
export class FuenteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fuente }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FuenteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fuente = fuente;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/fuente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/fuente', { outlets: { popup: null } }]);
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
