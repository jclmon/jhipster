// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { ProductoComponentsPage, ProductoDeleteDialog, ProductoUpdatePage } from './producto.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Producto e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productoUpdatePage: ProductoUpdatePage;
  let productoComponentsPage: ProductoComponentsPage;
  /* let productoDeleteDialog: ProductoDeleteDialog; */
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Productos', async () => {
    await navBarPage.goToEntity('producto');
    productoComponentsPage = new ProductoComponentsPage();
    await browser.wait(ec.visibilityOf(productoComponentsPage.title), 5000);
    expect(await productoComponentsPage.getTitle()).to.eq('lastcrmApp.producto.home.title');
  });

  it('should load create Producto page', async () => {
    await productoComponentsPage.clickOnCreateButton();
    productoUpdatePage = new ProductoUpdatePage();
    expect(await productoUpdatePage.getPageTitle()).to.eq('lastcrmApp.producto.home.createOrEditLabel');
    await productoUpdatePage.cancel();
  });

  /*  it('should create and save Productos', async () => {
        const nbButtonsBeforeCreate = await productoComponentsPage.countDeleteButtons();

        await productoComponentsPage.clickOnCreateButton();
        await promise.all([
            productoUpdatePage.setDireccionInput('direccion'),
            productoUpdatePage.setComentarioInput('comentario'),
            productoUpdatePage.destinoSelectLastOption(),
            productoUpdatePage.setPrecioInput('5'),
            productoUpdatePage.setImage1Input(absolutePath),
            productoUpdatePage.setImage2Input(absolutePath),
            productoUpdatePage.setImage3Input(absolutePath),
            productoUpdatePage.setImage4Input(absolutePath),
            productoUpdatePage.setImage5Input(absolutePath),
            productoUpdatePage.setPrecioAnteriorInput('5'),
            productoUpdatePage.setDormitoriosInput('5'),
            productoUpdatePage.setAseosInput('5'),
            productoUpdatePage.setMetrosInput('5'),
            productoUpdatePage.setGarageInput('5'),
            productoUpdatePage.setAnioconstruccionInput('5'),
            productoUpdatePage.municipioSelectLastOption(),
            productoUpdatePage.tipoProductoSelectLastOption(),
        ]);
        expect(await productoUpdatePage.getDireccionInput()).to.eq('direccion', 'Expected Direccion value to be equals to direccion');
        expect(await productoUpdatePage.getComentarioInput()).to.eq('comentario', 'Expected Comentario value to be equals to comentario');
        expect(await productoUpdatePage.getPrecioInput()).to.eq('5', 'Expected precio value to be equals to 5');
        expect(await productoUpdatePage.getImage1Input()).to.endsWith(fileNameToUpload, 'Expected Image1 value to be end with ' + fileNameToUpload);
        expect(await productoUpdatePage.getImage2Input()).to.endsWith(fileNameToUpload, 'Expected Image2 value to be end with ' + fileNameToUpload);
        expect(await productoUpdatePage.getImage3Input()).to.endsWith(fileNameToUpload, 'Expected Image3 value to be end with ' + fileNameToUpload);
        expect(await productoUpdatePage.getImage4Input()).to.endsWith(fileNameToUpload, 'Expected Image4 value to be end with ' + fileNameToUpload);
        expect(await productoUpdatePage.getImage5Input()).to.endsWith(fileNameToUpload, 'Expected Image5 value to be end with ' + fileNameToUpload);
        expect(await productoUpdatePage.getPrecioAnteriorInput()).to.eq('5', 'Expected precioAnterior value to be equals to 5');
        expect(await productoUpdatePage.getDormitoriosInput()).to.eq('5', 'Expected dormitorios value to be equals to 5');
        expect(await productoUpdatePage.getAseosInput()).to.eq('5', 'Expected aseos value to be equals to 5');
        expect(await productoUpdatePage.getMetrosInput()).to.eq('5', 'Expected metros value to be equals to 5');
        expect(await productoUpdatePage.getGarageInput()).to.eq('5', 'Expected garage value to be equals to 5');
        expect(await productoUpdatePage.getAnioconstruccionInput()).to.eq('5', 'Expected anioconstruccion value to be equals to 5');
        await productoUpdatePage.save();
        expect(await productoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await productoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Producto', async () => {
        const nbButtonsBeforeDelete = await productoComponentsPage.countDeleteButtons();
        await productoComponentsPage.clickOnLastDeleteButton();

        productoDeleteDialog = new ProductoDeleteDialog();
        expect(await productoDeleteDialog.getDialogTitle())
            .to.eq('lastcrmApp.producto.delete.question');
        await productoDeleteDialog.clickOnConfirmButton();

        expect(await productoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
