import { element, by, ElementFinder } from 'protractor';

export class FichaClienteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-ficha-cliente div table .btn-danger'));
  title = element.all(by.css('jhi-ficha-cliente div h2#page-heading span')).first();

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

export class FichaClienteUpdatePage {
  pageTitle = element(by.id('jhi-ficha-cliente-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  solicitudSelect = element(by.id('field_solicitud'));
  prioridadSelect = element(by.id('field_prioridad'));
  comentarioInput = element(by.id('field_comentario'));
  clienteSelect = element(by.id('field_cliente'));
  productoSelect = element(by.id('field_producto'));
  areaSelect = element(by.id('field_area'));
  citaSelect = element(by.id('field_cita'));
  tipoProductoSelect = element(by.id('field_tipoProducto'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSolicitudSelect(solicitud) {
    await this.solicitudSelect.sendKeys(solicitud);
  }

  async getSolicitudSelect() {
    return await this.solicitudSelect.element(by.css('option:checked')).getText();
  }

  async solicitudSelectLastOption(timeout?: number) {
    await this.solicitudSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setPrioridadSelect(prioridad) {
    await this.prioridadSelect.sendKeys(prioridad);
  }

  async getPrioridadSelect() {
    return await this.prioridadSelect.element(by.css('option:checked')).getText();
  }

  async prioridadSelectLastOption(timeout?: number) {
    await this.prioridadSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setComentarioInput(comentario) {
    await this.comentarioInput.sendKeys(comentario);
  }

  async getComentarioInput() {
    return await this.comentarioInput.getAttribute('value');
  }

  async clienteSelectLastOption(timeout?: number) {
    await this.clienteSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async clienteSelectOption(option) {
    await this.clienteSelect.sendKeys(option);
  }

  getClienteSelect(): ElementFinder {
    return this.clienteSelect;
  }

  async getClienteSelectedOption() {
    return await this.clienteSelect.element(by.css('option:checked')).getText();
  }

  async productoSelectLastOption(timeout?: number) {
    await this.productoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productoSelectOption(option) {
    await this.productoSelect.sendKeys(option);
  }

  getProductoSelect(): ElementFinder {
    return this.productoSelect;
  }

  async getProductoSelectedOption() {
    return await this.productoSelect.element(by.css('option:checked')).getText();
  }

  async areaSelectLastOption(timeout?: number) {
    await this.areaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async areaSelectOption(option) {
    await this.areaSelect.sendKeys(option);
  }

  getAreaSelect(): ElementFinder {
    return this.areaSelect;
  }

  async getAreaSelectedOption() {
    return await this.areaSelect.element(by.css('option:checked')).getText();
  }

  async citaSelectLastOption(timeout?: number) {
    await this.citaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async citaSelectOption(option) {
    await this.citaSelect.sendKeys(option);
  }

  getCitaSelect(): ElementFinder {
    return this.citaSelect;
  }

  async getCitaSelectedOption() {
    return await this.citaSelect.element(by.css('option:checked')).getText();
  }

  async tipoProductoSelectLastOption(timeout?: number) {
    await this.tipoProductoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async tipoProductoSelectOption(option) {
    await this.tipoProductoSelect.sendKeys(option);
  }

  getTipoProductoSelect(): ElementFinder {
    return this.tipoProductoSelect;
  }

  async getTipoProductoSelectedOption() {
    return await this.tipoProductoSelect.element(by.css('option:checked')).getText();
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

export class FichaClienteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-fichaCliente-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-fichaCliente'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
