import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFichaCliente } from 'app/shared/model/ficha-cliente.model';
import { FichaClienteService } from './ficha-cliente.service';

@Component({
  selector: 'jhi-ficha-cliente-delete-dialog',
  templateUrl: './ficha-cliente-delete-dialog.component.html'
})
export class FichaClienteDeleteDialogComponent {
  fichaCliente: IFichaCliente;

  constructor(
    protected fichaClienteService: FichaClienteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fichaClienteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fichaClienteListModification',
        content: 'Deleted an fichaCliente'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ficha-cliente-delete-popup',
  template: ''
})
export class FichaClienteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fichaCliente }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FichaClienteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fichaCliente = fichaCliente;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ficha-cliente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ficha-cliente', { outlets: { popup: null } }]);
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
