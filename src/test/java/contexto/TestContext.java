package contexto;

import factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import pageObjects.PaginaInicial;
import pageObjects.PaginaLogin;

/**
 * Contexto de teste para compartilhar estado entre steps.
 * Utilizado pelo PicoContainer para injeção de dependência.
 */
public class TestContext {

    private WebDriver driver;
    private PaginaLogin paginaLogin;
    private PaginaInicial paginaInicial;

    /**
     * Obtém a instância do WebDriver.
     * Cria uma nova instância se não existir.
     */
    public WebDriver getDriver() {
        if (driver == null) {
            driver = DriverFactory.getDriver();
        }
        return driver;
    }

    /**
     * Obtém a instância da Página de Login.
     * Cria uma nova instância se não existir.
     */
    public PaginaLogin getPaginaLogin() {
        if (paginaLogin == null) {
            paginaLogin = new PaginaLogin(getDriver());
        }
        return paginaLogin;
    }

    /**
     * Obtém a instância da Página Inicial.
     */
    public PaginaInicial getPaginaInicial() {
        return paginaInicial;
    }

    /**
     * Define a instância da Página Inicial.
     */
    public void setPaginaInicial(PaginaInicial paginaInicial) {
        this.paginaInicial = paginaInicial;
    }

    /**
     * Limpa o contexto e encerra o WebDriver.
     */
    public void limparContexto() {
        DriverFactory.quitDriver();
        driver = null;
        paginaLogin = null;
        paginaInicial = null;
    }
}
