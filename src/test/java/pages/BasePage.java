package pages;

import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Base de todas as páginas. Concentra as ações do navegador (clicar, digitar, ler texto)
 * e TODAS elas esperam o elemento ficar pronto antes de agir (espera explícita).
 * Nunca usamos Thread.sleep: esperas fixas deixam o teste lento e instável.
 */
public abstract class BasePage {

    private static final Logger log = LoggerFactory.getLogger(BasePage.class);

    protected final WebDriver driver;
    private final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.timeoutSeconds()));
    }

    protected void click(By locator) {
        log.debug("Clicando em {}", locator);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /** Digita um texto no campo. O texto não é registrado no log, pois pode ser uma senha. */
    protected void type(By locator, String texto) {
        log.debug("Digitando em {}", locator);
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        campo.clear();
        campo.sendKeys(texto);
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    /** Espera o elemento aparecer. Retorna false se ele não aparecer dentro do tempo limite. */
    protected boolean isVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Espera o elemento sumir (ou confirma que ele já não existe). */
    protected boolean isGone(By locator) {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return false;
        }
    }
}
