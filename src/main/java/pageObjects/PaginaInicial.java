package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object para a página inicial após login.
 */
public class PaginaInicial extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(PaginaInicial.class);

    // Locators
    private final By welcomeMessage = By.id("welcomeMessage");
    private final By userMenu = By.cssSelector(".user-menu");
    private final By logoutButton = By.cssSelector(".logout-button");

    public PaginaInicial(WebDriver driver) {
        super(driver);
    }

    /**
     * Obtém mensagem de boas-vindas
     */
    public String getMensagemBoasVindas() {
        logger.info("Obtendo mensagem de boas-vindas");
        return getText(welcomeMessage);
    }

    /**
     * Verifica se mensagem de boas-vindas contém texto esperado
     */
    public boolean isMensagemBoasVindasContem(String texto) {
        return getMensagemBoasVindas().contains(texto);
    }

    /**
     * Verifica se está na página inicial
     */
    public boolean isPaginaInicial() {
        return isDisplayed(welcomeMessage);
    }

    /**
     * Clica no menu do usuário
     */
    public PaginaInicial clicarMenuUsuario() {
        logger.info("Clicando no menu do usuário");
        click(userMenu);
        return this;
    }

    /**
     * Realiza logout
     */
    public PaginaLogin realizarLogout() {
        logger.info("Realizando logout");
        clicarMenuUsuario();
        click(logoutButton);
        return new PaginaLogin(driver);
    }
}