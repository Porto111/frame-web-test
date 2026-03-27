package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Classe base para todos os Page Objects.
 * Fornece métodos comuns de interação com elementos da página.
 */
public abstract class BasePage {

    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final int DEFAULT_TIMEOUT = 10;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    /**
     * Navega para uma URL específica.
     *
     * @param url URL para navegar
     */
    public void navigateTo(String url) {
        logger.info("Navegando para: {}", url);
        driver.get(url);
    }

    /**
     * Obtém a URL atual do navegador.
     *
     * @return URL atual
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Obtém o título da página atual.
     *
     * @return título da página
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Clica em um elemento.
     *
     * @param locator By locator do elemento
     */
    protected void click(By locator) {
        logger.debug("Clicando no elemento: {}", locator);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    /**
     * Preenche um campo de texto.
     *
     * @param locator By locator do elemento
     * @param text texto a ser preenchido
     */
    protected void sendKeys(By locator, String text) {
        logger.debug("Preenchendo elemento {} com texto: {}", locator, text);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Obtém o texto de um elemento.
     *
     * @param locator By locator do elemento
     * @return texto do elemento
     */
    protected String getText(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText();
    }

    /**
     * Verifica se um elemento está visível.
     *
     * @param locator By locator do elemento
     * @return true se visível, false caso contrário
     */
    protected boolean isDisplayed(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica se um elemento está presente na página.
     *
     * @param locator By locator do elemento
     * @return true se presente, false caso contrário
     */
    protected boolean isPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Aguarda até que um elemento esteja visível.
     *
     * @param locator By locator do elemento
     * @return WebElement encontrado
     */
    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Aguarda até que um elemento esteja clicável.
     *
     * @param locator By locator do elemento
     * @return WebElement encontrado
     */
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
