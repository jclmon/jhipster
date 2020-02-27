import { element, by, ElementFinder } from 'protractor';

export class AreaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-area div table .btn-danger'));
  title = element.all(by.css('jhi-area div h2#page-heading span')).first();

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

export class AreaUpdatePage {
  pageTitle = element(by.id('jhi-area-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nombreInput = element(by.id('field_nombre'));
  municipioSelect = element(by.id('field_municipio'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNombreInput(nombre) {
    await this.nombreInput.sendKeys(nombre);
  }

  async getNombreInput() {
    return await this.nombreInput.getAttribute('value');
  }

  async municipioSelectLastOption(timeout?: number) {
    await this.municipioSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async municipioSelectOption(option) {
    await this.municipioSelect.sendKeys(option);
  }

  getMunicipioSelect(): ElementFinder {
    return this.municipioSelect;
  }

  async getMunicipioSelectedOption() {
    return await this.municipioSelect.element(by.css('option:checked')).getText();
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

export class AreaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-area-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-area'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
