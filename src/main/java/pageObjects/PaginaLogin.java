package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object para a página de Login.
 * Encapsula todos os elementos e ações relacionadas ao login.
 */
public class PaginaLogin extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(PaginaLogin.class);

    // Locators
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("loginButton");
    private final By errorMessage = By.cssSelector(".error-message");

    public PaginaLogin(WebDriver driver) {
        super(driver);
    }

    /**
     * Preenche o campo de usuário
     */
    public PaginaLogin preencherUsuario(String username) {
        logger.info("Preenchendo usuário: {}", username);
        sendKeys(usernameField, username);
        return this;
    }

    /**
     * Preenche o campo de senha
     */
    public PaginaLogin preencherSenha(String password) {
        logger.info("Preenchendo senha");
        sendKeys(passwordField, password);
        return this;
    }

    /**
     * Clica no botão de login
     */
    public PaginaInicial clicarBotaoLogin() {
        logger.info("Clicando no botão de login");
        click(loginButton);
        return new PaginaInicial(driver);
    }

    /**
     * Realiza login completo e retorna a página inicial
     */
    public PaginaInicial realizarLogin(String username, String password) {
        logger.info("Realizando login com usuário: {}", username);
        preencherUsuario(username)
                .preencherSenha(password)
                .clicarBotaoLogin();
        return new PaginaInicial(driver);
    }

    /**
     * Verifica se mensagem de erro está visível
     */
    public boolean isMensagemErroVisivel() {
        return isDisplayed(errorMessage);
    }

    /**
     * Obtém texto da mensagem de erro
     */
    public String getMensagemErro() {
        return getText(errorMessage);
    }

    /**
     * Verifica se está na página de login
     */
    public boolean isPaginaLogin() {
        return isPresent(loginButton);
    }
}