import { element, by, ElementFinder } from 'protractor';

export class ClienteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cliente div table .btn-danger'));
  title = element.all(by.css('jhi-cliente div h2#page-heading span')).first();

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

export class ClienteUpdatePage {
  pageTitle = element(by.id('jhi-cliente-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nombreInput = element(by.id('field_nombre'));
  apellido1Input = element(by.id('field_apellido1'));
  apellido2Input = element(by.id('field_apellido2'));
  nifInput = element(by.id('field_nif'));
  generoSelect = element(by.id('field_genero'));
  tlfMovilInput = element(by.id('field_tlfMovil'));
  tlfMovil2Input = element(by.id('field_tlfMovil2'));
  tlfFijoInput = element(by.id('field_tlfFijo'));
  faxInput = element(by.id('field_fax'));
  email1Input = element(by.id('field_email1'));
  email2Input = element(by.id('field_email2'));
  fuenteSelect = element(by.id('field_fuente'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNombreInput(nombre) {
    await this.nombreInput.sendKeys(nombre);
  }

  async getNombreInput() {
    return await this.nombreInput.getAttribute('value');
  }

  async setApellido1Input(apellido1) {
    await this.apellido1Input.sendKeys(apellido1);
  }

  async getApellido1Input() {
    return await this.apellido1Input.getAttribute('value');
  }

  async setApellido2Input(apellido2) {
    await this.apellido2Input.sendKeys(apellido2);
  }

  async getApellido2Input() {
    return await this.apellido2Input.getAttribute('value');
  }

  async setNifInput(nif) {
    await this.nifInput.sendKeys(nif);
  }

  async getNifInput() {
    return await this.nifInput.getAttribute('value');
  }

  async setGeneroSelect(genero) {
    await this.generoSelect.sendKeys(genero);
  }

  async getGeneroSelect() {
    return await this.generoSelect.element(by.css('option:checked')).getText();
  }

  async generoSelectLastOption(timeout?: number) {
    await this.generoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setTlfMovilInput(tlfMovil) {
    await this.tlfMovilInput.sendKeys(tlfMovil);
  }

  async getTlfMovilInput() {
    return await this.tlfMovilInput.getAttribute('value');
  }

  async setTlfMovil2Input(tlfMovil2) {
    await this.tlfMovil2Input.sendKeys(tlfMovil2);
  }

  async getTlfMovil2Input() {
    return await this.tlfMovil2Input.getAttribute('value');
  }

  async setTlfFijoInput(tlfFijo) {
    await this.tlfFijoInput.sendKeys(tlfFijo);
  }

  async getTlfFijoInput() {
    return await this.tlfFijoInput.getAttribute('value');
  }

  async setFaxInput(fax) {
    await this.faxInput.sendKeys(fax);
  }

  async getFaxInput() {
    return await this.faxInput.getAttribute('value');
  }

  async setEmail1Input(email1) {
    await this.email1Input.sendKeys(email1);
  }

  async getEmail1Input() {
    return await this.email1Input.getAttribute('value');
  }

  async setEmail2Input(email2) {
    await this.email2Input.sendKeys(email2);
  }

  async getEmail2Input() {
    return await this.email2Input.getAttribute('value');
  }

  async fuenteSelectLastOption(timeout?: number) {
    await this.fuenteSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async fuenteSelectOption(option) {
    await this.fuenteSelect.sendKeys(option);
  }

  getFuenteSelect(): ElementFinder {
    return this.fuenteSelect;
  }

  async getFuenteSelectedOption() {
    return await this.fuenteSelect.element(by.css('option:checked')).getText();
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

export class ClienteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cliente-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cliente'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
