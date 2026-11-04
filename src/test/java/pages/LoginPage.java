package pages;

import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Página de login. Só conhece os elementos e as ações da tela; as verificações ficam nos steps. */
public class LoginPage extends BasePage {

    private final By campoUsuario = By.id("user-name");
    private final By campoSenha = By.id("password");
    private final By botaoLogin = By.id("login-button");
    private final By mensagemErro = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(Config.baseUrl());
    }

    public void loginAs(String usuario, String senha) {
        type(campoUsuario, usuario);
        type(campoSenha, senha);
        click(botaoLogin);
    }

    public boolean isDisplayed() {
        return isVisible(botaoLogin);
    }

    public String getErrorMessage() {
        return getText(mensagemErro);
    }
}
