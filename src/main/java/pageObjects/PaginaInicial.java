package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaginaInicial {
    private WebDriver driver;
    private By welcomeMessage = By.id("welcomeMessage");

    public PaginaInicial(WebDriver driver) {
        this.driver = driver;
    }

    public String getWelcomeMessage() {
        return driver.findElement(welcomeMessage).getText();
    }
}
