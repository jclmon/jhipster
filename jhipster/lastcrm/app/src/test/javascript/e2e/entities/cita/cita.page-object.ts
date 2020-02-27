import { element, by, ElementFinder } from 'protractor';

export class CitaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cita div table .btn-danger'));
  title = element.all(by.css('jhi-cita div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class CitaUpdatePage {
  pageTitle = element(by.id('jhi-cita-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  fechaInput = element(by.id('field_fecha'));
  comentarioInput = element(by.id('field_comentario'));
  estadoSelect = element(by.id('field_estado'));
  agenteSelect = element(by.id('field_agente'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFechaInput(fecha) {
    await this.fechaInput.sendKeys(fecha);
  }

  async getFechaInput() {
    return await this.fechaInput.getAttribute('value');
  }

  async setComentarioInput(comentario) {
    await this.comentarioInput.sendKeys(comentario);
  }

  async getComentarioInput() {
    return await this.comentarioInput.getAttribute('value');
  }

  async setEstadoSelect(estado) {
    await this.estadoSelect.sendKeys(estado);
  }

  async getEstadoSelect() {
    return await this.estadoSelect.element(by.css('option:checked')).getText();
  }

  async estadoSelectLastOption(timeout?: number) {
    await this.estadoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async agenteSelectLastOption(timeout?: number) {
    await this.agenteSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async agenteSelectOption(option) {
    await this.agenteSelect.sendKeys(option);
  }

  getAgenteSelect(): ElementFinder {
    return this.agenteSelect;
  }

  async getAgenteSelectedOption() {
    return await this.agenteSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class CitaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cita-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cita'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
