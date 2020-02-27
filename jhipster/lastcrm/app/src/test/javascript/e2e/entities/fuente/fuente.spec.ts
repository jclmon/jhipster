// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FuenteComponentsPage, FuenteDeleteDialog, FuenteUpdatePage } from './fuente.page-object';

const expect = chai.expect;

describe('Fuente e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fuenteUpdatePage: FuenteUpdatePage;
  let fuenteComponentsPage: FuenteComponentsPage;
  let fuenteDeleteDialog: FuenteDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Fuentes', async () => {
    await navBarPage.goToEntity('fuente');
    fuenteComponentsPage = new FuenteComponentsPage();
    await browser.wait(ec.visibilityOf(fuenteComponentsPage.title), 5000);
    expect(await fuenteComponentsPage.getTitle()).to.eq('lastcrmApp.fuente.home.title');
  });

  it('should load create Fuente page', async () => {
    await fuenteComponentsPage.clickOnCreateButton();
    fuenteUpdatePage = new FuenteUpdatePage();
    expect(await fuenteUpdatePage.getPageTitle()).to.eq('lastcrmApp.fuente.home.createOrEditLabel');
    await fuenteUpdatePage.cancel();
  });

  it('should create and save Fuentes', async () => {
    const nbButtonsBeforeCreate = await fuenteComponentsPage.countDeleteButtons();

    await fuenteComponentsPage.clickOnCreateButton();
    await promise.all([fuenteUpdatePage.setNombreInput('nombre')]);
    expect(await fuenteUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    await fuenteUpdatePage.save();
    expect(await fuenteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await fuenteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Fuente', async () => {
    const nbButtonsBeforeDelete = await fuenteComponentsPage.countDeleteButtons();
    await fuenteComponentsPage.clickOnLastDeleteButton();

    fuenteDeleteDialog = new FuenteDeleteDialog();
    expect(await fuenteDeleteDialog.getDialogTitle()).to.eq('lastcrmApp.fuente.delete.question');
    await fuenteDeleteDialog.clickOnConfirmButton();

    expect(await fuenteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
