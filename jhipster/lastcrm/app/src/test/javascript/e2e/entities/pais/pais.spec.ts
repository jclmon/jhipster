// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { PaisComponentsPage, PaisDeleteDialog, PaisUpdatePage } from './pais.page-object';

const expect = chai.expect;

describe('Pais e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paisUpdatePage: PaisUpdatePage;
  let paisComponentsPage: PaisComponentsPage;
  let paisDeleteDialog: PaisDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Pais', async () => {
    await navBarPage.goToEntity('pais');
    paisComponentsPage = new PaisComponentsPage();
    await browser.wait(ec.visibilityOf(paisComponentsPage.title), 5000);
    expect(await paisComponentsPage.getTitle()).to.eq('lastcrmApp.pais.home.title');
  });

  it('should load create Pais page', async () => {
    await paisComponentsPage.clickOnCreateButton();
    paisUpdatePage = new PaisUpdatePage();
    expect(await paisUpdatePage.getPageTitle()).to.eq('lastcrmApp.pais.home.createOrEditLabel');
    await paisUpdatePage.cancel();
  });

  it('should create and save Pais', async () => {
    const nbButtonsBeforeCreate = await paisComponentsPage.countDeleteButtons();

    await paisComponentsPage.clickOnCreateButton();
    await promise.all([paisUpdatePage.setNombreInput('nombre')]);
    expect(await paisUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    await paisUpdatePage.save();
    expect(await paisUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paisComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Pais', async () => {
    const nbButtonsBeforeDelete = await paisComponentsPage.countDeleteButtons();
    await paisComponentsPage.clickOnLastDeleteButton();

    paisDeleteDialog = new PaisDeleteDialog();
    expect(await paisDeleteDialog.getDialogTitle()).to.eq('lastcrmApp.pais.delete.question');
    await paisDeleteDialog.clickOnConfirmButton();

    expect(await paisComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
