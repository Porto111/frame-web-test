package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import driver.DriverFactory;
import pages.LoginPage;
import pages.ProductsPage;

import static org.assertj.core.api.Assertions.assertThat;

/** Passos dos cenários de login e logout. As verificações (assertions) ficam aqui, nos steps. */
public class LoginSteps {

    private final LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
    private final ProductsPage productsPage = new ProductsPage(DriverFactory.getDriver());

    @Given("que estou na página de login")
    public void queEstouNaPaginaDeLogin() {
        loginPage.open();
        assertThat(loginPage.isDisplayed())
                .as("A página de login deveria estar visível")
                .isTrue();
    }

    // Passo reutilizado por outras features (ex.: carrinho) para chegar logado à loja
    @Given("que estou logado como {string} com a senha {string}")
    public void queEstouLogadoComo(String usuario, String senha) {
        loginPage.open();
        loginPage.loginAs(usuario, senha);
        assertThat(productsPage.isLoaded())
                .as("O login deveria levar à página de produtos")
                .isTrue();
    }

    @When("eu faço login com o usuário {string} e a senha {string}")
    public void euFacoLoginComOUsuarioEASenha(String usuario, String senha) {
        loginPage.loginAs(usuario, senha);
    }

    @When("eu faço logout")
    public void euFacoLogout() {
        productsPage.logout();
    }

    @Then("devo ver a página de produtos")
    public void devoVerAPaginaDeProdutos() {
        assertThat(productsPage.isLoaded())
                .as("A página de produtos deveria estar visível")
                .isTrue();
        assertThat(productsPage.getTitle()).isEqualTo("Products");
    }

    @Then("devo ver a mensagem de erro {string}")
    public void devoVerAMensagemDeErro(String mensagemEsperada) {
        assertThat(loginPage.getErrorMessage())
                .as("Mensagem de erro exibida no login")
                .contains(mensagemEsperada);
    }

    @Then("devo estar na página de login")
    public void devoEstarNaPaginaDeLogin() {
        assertThat(loginPage.isDisplayed())
                .as("Após o logout, a página de login deveria estar visível")
                .isTrue();
    }
}
