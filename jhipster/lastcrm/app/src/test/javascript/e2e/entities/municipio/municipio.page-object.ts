import { element, by, ElementFinder } from 'protractor';

export class MunicipioComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-municipio div table .btn-danger'));
  title = element.all(by.css('jhi-municipio div h2#page-heading span')).first();

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

export class MunicipioUpdatePage {
  pageTitle = element(by.id('jhi-municipio-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nombreInput = element(by.id('field_nombre'));
  codPostalInput = element(by.id('field_codPostal'));
  provinciaSelect = element(by.id('field_provincia'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNombreInput(nombre) {
    await this.nombreInput.sendKeys(nombre);
  }

  async getNombreInput() {
    return await this.nombreInput.getAttribute('value');
  }

  async setCodPostalInput(codPostal) {
    await this.codPostalInput.sendKeys(codPostal);
  }

  async getCodPostalInput() {
    return await this.codPostalInput.getAttribute('value');
  }

  async provinciaSelectLastOption(timeout?: number) {
    await this.provinciaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async provinciaSelectOption(option) {
    await this.provinciaSelect.sendKeys(option);
  }

  getProvinciaSelect(): ElementFinder {
    return this.provinciaSelect;
  }

  async getProvinciaSelectedOption() {
    return await this.provinciaSelect.element(by.css('option:checked')).getText();
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

export class MunicipioDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-municipio-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-municipio'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
