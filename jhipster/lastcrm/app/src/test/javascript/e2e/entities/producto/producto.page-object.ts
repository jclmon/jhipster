import { element, by, ElementFinder } from 'protractor';

export class ProductoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-producto div table .btn-danger'));
  title = element.all(by.css('jhi-producto div h2#page-heading span')).first();

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

export class ProductoUpdatePage {
  pageTitle = element(by.id('jhi-producto-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  direccionInput = element(by.id('field_direccion'));
  comentarioInput = element(by.id('field_comentario'));
  destinoSelect = element(by.id('field_destino'));
  precioInput = element(by.id('field_precio'));
  image1Input = element(by.id('file_image1'));
  image2Input = element(by.id('file_image2'));
  image3Input = element(by.id('file_image3'));
  image4Input = element(by.id('file_image4'));
  image5Input = element(by.id('file_image5'));
  precioAnteriorInput = element(by.id('field_precioAnterior'));
  dormitoriosInput = element(by.id('field_dormitorios'));
  aseosInput = element(by.id('field_aseos'));
  metrosInput = element(by.id('field_metros'));
  garageInput = element(by.id('field_garage'));
  anioconstruccionInput = element(by.id('field_anioconstruccion'));
  municipioSelect = element(by.id('field_municipio'));
  tipoProductoSelect = element(by.id('field_tipoProducto'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDireccionInput(direccion) {
    await this.direccionInput.sendKeys(direccion);
  }

  async getDireccionInput() {
    return await this.direccionInput.getAttribute('value');
  }

  async setComentarioInput(comentario) {
    await this.comentarioInput.sendKeys(comentario);
  }

  async getComentarioInput() {
    return await this.comentarioInput.getAttribute('value');
  }

  async setDestinoSelect(destino) {
    await this.destinoSelect.sendKeys(destino);
  }

  async getDestinoSelect() {
    return await this.destinoSelect.element(by.css('option:checked')).getText();
  }

  async destinoSelectLastOption(timeout?: number) {
    await this.destinoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setPrecioInput(precio) {
    await this.precioInput.sendKeys(precio);
  }

  async getPrecioInput() {
    return await this.precioInput.getAttribute('value');
  }

  async setImage1Input(image1) {
    await this.image1Input.sendKeys(image1);
  }

  async getImage1Input() {
    return await this.image1Input.getAttribute('value');
  }

  async setImage2Input(image2) {
    await this.image2Input.sendKeys(image2);
  }

  async getImage2Input() {
    return await this.image2Input.getAttribute('value');
  }

  async setImage3Input(image3) {
    await this.image3Input.sendKeys(image3);
  }

  async getImage3Input() {
    return await this.image3Input.getAttribute('value');
  }

  async setImage4Input(image4) {
    await this.image4Input.sendKeys(image4);
  }

  async getImage4Input() {
    return await this.image4Input.getAttribute('value');
  }

  async setImage5Input(image5) {
    await this.image5Input.sendKeys(image5);
  }

  async getImage5Input() {
    return await this.image5Input.getAttribute('value');
  }

  async setPrecioAnteriorInput(precioAnterior) {
    await this.precioAnteriorInput.sendKeys(precioAnterior);
  }

  async getPrecioAnteriorInput() {
    return await this.precioAnteriorInput.getAttribute('value');
  }

  async setDormitoriosInput(dormitorios) {
    await this.dormitoriosInput.sendKeys(dormitorios);
  }

  async getDormitoriosInput() {
    return await this.dormitoriosInput.getAttribute('value');
  }

  async setAseosInput(aseos) {
    await this.aseosInput.sendKeys(aseos);
  }

  async getAseosInput() {
    return await this.aseosInput.getAttribute('value');
  }

  async setMetrosInput(metros) {
    await this.metrosInput.sendKeys(metros);
  }

  async getMetrosInput() {
    return await this.metrosInput.getAttribute('value');
  }

  async setGarageInput(garage) {
    await this.garageInput.sendKeys(garage);
  }

  async getGarageInput() {
    return await this.garageInput.getAttribute('value');
  }

  async setAnioconstruccionInput(anioconstruccion) {
    await this.anioconstruccionInput.sendKeys(anioconstruccion);
  }

  async getAnioconstruccionInput() {
    return await this.anioconstruccionInput.getAttribute('value');
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

export class ProductoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-producto-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-producto'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
