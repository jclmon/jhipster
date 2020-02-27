// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { CitaComponentsPage, CitaDeleteDialog, CitaUpdatePage } from './cita.page-object';

const expect = chai.expect;

describe('Cita e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let citaUpdatePage: CitaUpdatePage;
  let citaComponentsPage: CitaComponentsPage;
  /* let citaDeleteDialog: CitaDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Citas', async () => {
    await navBarPage.goToEntity('cita');
    citaComponentsPage = new CitaComponentsPage();
    await browser.wait(ec.visibilityOf(citaComponentsPage.title), 5000);
    expect(await citaComponentsPage.getTitle()).to.eq('lastcrmApp.cita.home.title');
  });

  it('should load create Cita page', async () => {
    await citaComponentsPage.clickOnCreateButton();
    citaUpdatePage = new CitaUpdatePage();
    expect(await citaUpdatePage.getPageTitle()).to.eq('lastcrmApp.cita.home.createOrEditLabel');
    await citaUpdatePage.cancel();
  });

  /*  it('should create and save Citas', async () => {
        const nbButtonsBeforeCreate = await citaComponentsPage.countDeleteButtons();

        await citaComponentsPage.clickOnCreateButton();
        await promise.all([
            citaUpdatePage.setFechaInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            citaUpdatePage.setComentarioInput('comentario'),
            citaUpdatePage.estadoSelectLastOption(),
            citaUpdatePage.agenteSelectLastOption(),
        ]);
        expect(await citaUpdatePage.getFechaInput()).to.contain('2001-01-01T02:30', 'Expected fecha value to be equals to 2000-12-31');
        expect(await citaUpdatePage.getComentarioInput()).to.eq('comentario', 'Expected Comentario value to be equals to comentario');
        await citaUpdatePage.save();
        expect(await citaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await citaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Cita', async () => {
        const nbButtonsBeforeDelete = await citaComponentsPage.countDeleteButtons();
        await citaComponentsPage.clickOnLastDeleteButton();

        citaDeleteDialog = new CitaDeleteDialog();
        expect(await citaDeleteDialog.getDialogTitle())
            .to.eq('lastcrmApp.cita.delete.question');
        await citaDeleteDialog.clickOnConfirmButton();

        expect(await citaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
