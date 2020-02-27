// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { TipoProductoComponentsPage, TipoProductoDeleteDialog, TipoProductoUpdatePage } from './tipo-producto.page-object';

const expect = chai.expect;

describe('TipoProducto e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tipoProductoUpdatePage: TipoProductoUpdatePage;
  let tipoProductoComponentsPage: TipoProductoComponentsPage;
  let tipoProductoDeleteDialog: TipoProductoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TipoProductos', async () => {
    await navBarPage.goToEntity('tipo-producto');
    tipoProductoComponentsPage = new TipoProductoComponentsPage();
    await browser.wait(ec.visibilityOf(tipoProductoComponentsPage.title), 5000);
    expect(await tipoProductoComponentsPage.getTitle()).to.eq('lastcrmApp.tipoProducto.home.title');
  });

  it('should load create TipoProducto page', async () => {
    await tipoProductoComponentsPage.clickOnCreateButton();
    tipoProductoUpdatePage = new TipoProductoUpdatePage();
    expect(await tipoProductoUpdatePage.getPageTitle()).to.eq('lastcrmApp.tipoProducto.home.createOrEditLabel');
    await tipoProductoUpdatePage.cancel();
  });

  it('should create and save TipoProductos', async () => {
    const nbButtonsBeforeCreate = await tipoProductoComponentsPage.countDeleteButtons();

    await tipoProductoComponentsPage.clickOnCreateButton();
    await promise.all([tipoProductoUpdatePage.setNombreInput('nombre')]);
    expect(await tipoProductoUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    await tipoProductoUpdatePage.save();
    expect(await tipoProductoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tipoProductoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TipoProducto', async () => {
    const nbButtonsBeforeDelete = await tipoProductoComponentsPage.countDeleteButtons();
    await tipoProductoComponentsPage.clickOnLastDeleteButton();

    tipoProductoDeleteDialog = new TipoProductoDeleteDialog();
    expect(await tipoProductoDeleteDialog.getDialogTitle()).to.eq('lastcrmApp.tipoProducto.delete.question');
    await tipoProductoDeleteDialog.clickOnConfirmButton();

    expect(await tipoProductoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
