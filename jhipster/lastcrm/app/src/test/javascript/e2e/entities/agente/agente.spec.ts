// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { AgenteComponentsPage, AgenteDeleteDialog, AgenteUpdatePage } from './agente.page-object';

const expect = chai.expect;

describe('Agente e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let agenteUpdatePage: AgenteUpdatePage;
  let agenteComponentsPage: AgenteComponentsPage;
  let agenteDeleteDialog: AgenteDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Agentes', async () => {
    await navBarPage.goToEntity('agente');
    agenteComponentsPage = new AgenteComponentsPage();
    await browser.wait(ec.visibilityOf(agenteComponentsPage.title), 5000);
    expect(await agenteComponentsPage.getTitle()).to.eq('lastcrmApp.agente.home.title');
  });

  it('should load create Agente page', async () => {
    await agenteComponentsPage.clickOnCreateButton();
    agenteUpdatePage = new AgenteUpdatePage();
    expect(await agenteUpdatePage.getPageTitle()).to.eq('lastcrmApp.agente.home.createOrEditLabel');
    await agenteUpdatePage.cancel();
  });

  it('should create and save Agentes', async () => {
    const nbButtonsBeforeCreate = await agenteComponentsPage.countDeleteButtons();

    await agenteComponentsPage.clickOnCreateButton();
    await promise.all([
      agenteUpdatePage.setNombreInput('nombre'),
      agenteUpdatePage.setApellido1Input('apellido1'),
      agenteUpdatePage.setApellido2Input('apellido2'),
      agenteUpdatePage.setTelefonoInput('telefono')
    ]);
    expect(await agenteUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
    expect(await agenteUpdatePage.getApellido1Input()).to.eq('apellido1', 'Expected Apellido1 value to be equals to apellido1');
    expect(await agenteUpdatePage.getApellido2Input()).to.eq('apellido2', 'Expected Apellido2 value to be equals to apellido2');
    expect(await agenteUpdatePage.getTelefonoInput()).to.eq('telefono', 'Expected Telefono value to be equals to telefono');
    await agenteUpdatePage.save();
    expect(await agenteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await agenteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Agente', async () => {
    const nbButtonsBeforeDelete = await agenteComponentsPage.countDeleteButtons();
    await agenteComponentsPage.clickOnLastDeleteButton();

    agenteDeleteDialog = new AgenteDeleteDialog();
    expect(await agenteDeleteDialog.getDialogTitle()).to.eq('lastcrmApp.agente.delete.question');
    await agenteDeleteDialog.clickOnConfirmButton();

    expect(await agenteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
