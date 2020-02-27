// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { ClienteComponentsPage, ClienteDeleteDialog, ClienteUpdatePage } from './cliente.page-object';

const expect = chai.expect;

describe('Cliente e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let clienteUpdatePage: ClienteUpdatePage;
  let clienteComponentsPage: ClienteComponentsPage;
  /* let clienteDeleteDialog: ClienteDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Clientes', async () => {
    await navBarPage.goToEntity('cliente');
    clienteComponentsPage = new ClienteComponentsPage();
    await browser.wait(ec.visibilityOf(clienteComponentsPage.title), 5000);
    expect(await clienteComponentsPage.getTitle()).to.eq('lastcrmApp.cliente.home.title');
  });

  it('should load create Cliente page', async () => {
    await clienteComponentsPage.clickOnCreateButton();
    clienteUpdatePage = new ClienteUpdatePage();
    expect(await clienteUpdatePage.getPageTitle()).to.eq('lastcrmApp.cliente.home.createOrEditLabel');
    await clienteUpdatePage.cancel();
  });

  /*  it('should create and save Clientes', async () => {
        const nbButtonsBeforeCreate = await clienteComponentsPage.countDeleteButtons();

        await clienteComponentsPage.clickOnCreateButton();
        await promise.all([
            clienteUpdatePage.setNombreInput('nombre'),
            clienteUpdatePage.setApellido1Input('apellido1'),
            clienteUpdatePage.setApellido2Input('apellido2'),
            clienteUpdatePage.setNifInput('nif'),
            clienteUpdatePage.generoSelectLastOption(),
            clienteUpdatePage.setTlfMovilInput('tlfMovil'),
            clienteUpdatePage.setTlfMovil2Input('tlfMovil2'),
            clienteUpdatePage.setTlfFijoInput('tlfFijo'),
            clienteUpdatePage.setFaxInput('fax'),
            clienteUpdatePage.setEmail1Input('email1'),
            clienteUpdatePage.setEmail2Input('email2'),
            clienteUpdatePage.fuenteSelectLastOption(),
        ]);
        expect(await clienteUpdatePage.getNombreInput()).to.eq('nombre', 'Expected Nombre value to be equals to nombre');
        expect(await clienteUpdatePage.getApellido1Input()).to.eq('apellido1', 'Expected Apellido1 value to be equals to apellido1');
        expect(await clienteUpdatePage.getApellido2Input()).to.eq('apellido2', 'Expected Apellido2 value to be equals to apellido2');
        expect(await clienteUpdatePage.getNifInput()).to.eq('nif', 'Expected Nif value to be equals to nif');
        expect(await clienteUpdatePage.getTlfMovilInput()).to.eq('tlfMovil', 'Expected TlfMovil value to be equals to tlfMovil');
        expect(await clienteUpdatePage.getTlfMovil2Input()).to.eq('tlfMovil2', 'Expected TlfMovil2 value to be equals to tlfMovil2');
        expect(await clienteUpdatePage.getTlfFijoInput()).to.eq('tlfFijo', 'Expected TlfFijo value to be equals to tlfFijo');
        expect(await clienteUpdatePage.getFaxInput()).to.eq('fax', 'Expected Fax value to be equals to fax');
        expect(await clienteUpdatePage.getEmail1Input()).to.eq('email1', 'Expected Email1 value to be equals to email1');
        expect(await clienteUpdatePage.getEmail2Input()).to.eq('email2', 'Expected Email2 value to be equals to email2');
        await clienteUpdatePage.save();
        expect(await clienteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await clienteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /*  it('should delete last Cliente', async () => {
        const nbButtonsBeforeDelete = await clienteComponentsPage.countDeleteButtons();
        await clienteComponentsPage.clickOnLastDeleteButton();

        clienteDeleteDialog = new ClienteDeleteDialog();
        expect(await clienteDeleteDialog.getDialogTitle())
            .to.eq('lastcrmApp.cliente.delete.question');
        await clienteDeleteDialog.clickOnConfirmButton();

        expect(await clienteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
