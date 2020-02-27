import { element, by, ElementFinder } from 'protractor';

export class AgenteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-agente div table .btn-danger'));
  title = element.all(by.css('jhi-agente div h2#page-heading span')).first();

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

export class AgenteUpdatePage {
  pageTitle = element(by.id('jhi-agente-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nombreInput = element(by.id('field_nombre'));
  apellido1Input = element(by.id('field_apellido1'));
  apellido2Input = element(by.id('field_apellido2'));
  telefonoInput = element(by.id('field_telefono'));

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

  async setTelefonoInput(telefono) {
    await this.telefonoInput.sendKeys(telefono);
  }

  async getTelefonoInput() {
    return await this.telefonoInput.getAttribute('value');
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

export class AgenteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-agente-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-agente'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
