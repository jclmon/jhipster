// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { MunicipioComponentsPage, MunicipioDeleteDialog, MunicipioUpdatePage } from './municipio.page-object';

const expect = chai.expect;

describe('Municipio e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let municipioUpdatePage: MunicipioUpdatePage;
  let municipioComponentsPage: MunicipioComponentsPage;
  /* let municipioDeleteDialog: MunicipioDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Municipios', async () => {
    await navBarPage.goToEntity('municipio');
    municipioComponentsPage = new MunicipioComponentsPage();
    await browser.wait(ec.visibilityOf(municipioComponentsPage.title), 5000);
    expect(await municipioComponentsPage.getTitle()).to.eq('lastcrmApp.municipio.home.title');
  });

  it('should load create Municipio page', async () => {
    await municipioComponentsPage.clickOnCreateButton();
    municipioUpdatePage = new MunicipioUpdatePage();
    expect(await municipioUpdatePage.getPageTitle()).to.eq('lastcrmApp.municipio.home.createOrEditLabel');
    await municipioUpdatePage.cancel();
  });

  /*  it('should create and save Municipios', async () => {
        const nbButtonsBeforeCreate = await municipioComponentsPage.countDeleteButtons();

        await municipioComponentsPage.clickOnCreateButton();
        await promise.all([
            municipioUpdatePage.setNombreInput('nombre'),
            municipioUpdatePage.setCodPostalInput('codPostal'),
            municipioUpdatePage.provinciaSelectLastOption(),
        ]);
        expect(await municipioUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
        expect(await municipioUpdatePage.getCodPostalInput()).to.eq('codPostal', 'Expected CodPostal value to be equals to codPostal');
        await municipioUpdatePage.save();
        expect(await municipioUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await municipioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Municipio', async () => {
        const nbButtonsBeforeDelete = await municipioComponentsPage.countDeleteButtons();
        await municipioComponentsPage.clickOnLastDeleteButton();

        municipioDeleteDialog = new MunicipioDeleteDialog();
        expect(await municipioDeleteDialog.getDialogTitle())
            .to.eq('lastcrmApp.municipio.delete.question');
        await municipioDeleteDialog.clickOnConfirmButton();

        expect(await municipioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
