import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgente } from 'app/shared/model/agente.model';
import { AgenteService } from './agente.service';

@Component({
  selector: 'jhi-agente-delete-dialog',
  templateUrl: './agente-delete-dialog.component.html'
})
export class AgenteDeleteDialogComponent {
  agente: IAgente;

  constructor(protected agenteService: AgenteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.agenteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'agenteListModification',
        content: 'Deleted an agente'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-agente-delete-popup',
  template: ''
})
export class AgenteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agente }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AgenteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.agente = agente;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/agente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/agente', { outlets: { popup: null } }]);
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
