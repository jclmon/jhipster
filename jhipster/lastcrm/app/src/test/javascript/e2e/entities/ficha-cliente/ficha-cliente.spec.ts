// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FichaClienteComponentsPage, FichaClienteDeleteDialog, FichaClienteUpdatePage } from './ficha-cliente.page-object';

const expect = chai.expect;

describe('FichaCliente e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fichaClienteUpdatePage: FichaClienteUpdatePage;
  let fichaClienteComponentsPage: FichaClienteComponentsPage;
  /* let fichaClienteDeleteDialog: FichaClienteDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FichaClientes', async () => {
    await navBarPage.goToEntity('ficha-cliente');
    fichaClienteComponentsPage = new FichaClienteComponentsPage();
    await browser.wait(ec.visibilityOf(fichaClienteComponentsPage.title), 5000);
    expect(await fichaClienteComponentsPage.getTitle()).to.eq('lastcrmApp.fichaCliente.home.title');
  });

  it('should load create FichaCliente page', async () => {
    await fichaClienteComponentsPage.clickOnCreateButton();
    fichaClienteUpdatePage = new FichaClienteUpdatePage();
    expect(await fichaClienteUpdatePage.getPageTitle()).to.eq('lastcrmApp.fichaCliente.home.createOrEditLabel');
    await fichaClienteUpdatePage.cancel();
  });

  /*  it('should create and save FichaClientes', async () => {
        const nbButtonsBeforeCreate = await fichaClienteComponentsPage.countDeleteButtons();

        await fichaClienteComponentsPage.clickOnCreateButton();
        await promise.all([
            fichaClienteUpdatePage.solicitudSelectLastOption(),
            fichaClienteUpdatePage.prioridadSelectLastOption(),
            fichaClienteUpdatePage.setComentarioInput('comentario'),
            fichaClienteUpdatePage.clienteSelectLastOption(),
            fichaClienteUpdatePage.productoSelectLastOption(),
            // fichaClienteUpdatePage.areaSelectLastOption(),
            // fichaClienteUpdatePage.citaSelectLastOption(),
            // fichaClienteUpdatePage.tipoProductoSelectLastOption(),
        ]);
        expect(await fichaClienteUpdatePage.getComentarioInput()).to.eq('comentario', 'Expected Comentario value to be equals to comentario');
        await fichaClienteUpdatePage.save();
        expect(await fichaClienteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await fichaClienteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last FichaCliente', async () => {
        const nbButtonsBeforeDelete = await fichaClienteComponentsPage.countDeleteButtons();
        await fichaClienteComponentsPage.clickOnLastDeleteButton();

        fichaClienteDeleteDialog = new FichaClienteDeleteDialog();
        expect(await fichaClienteDeleteDialog.getDialogTitle())
            .to.eq('lastcrmApp.fichaCliente.delete.question');
        await fichaClienteDeleteDialog.clickOnConfirmButton();

        expect(await fichaClienteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
