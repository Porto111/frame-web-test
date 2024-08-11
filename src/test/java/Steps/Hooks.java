package Steps;

import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import org.openqa.selenium.WebDriver;
import pageObjects.PaginaInicial;
import pageObjects.PaginaLogin;
import utils.PropertyReader;

public class Hooks {
    private WebDriver driver;
    private PaginaLogin paginaLogin;
    private PaginaInicial paginaInicial;
    private PropertyReader propertyReader;

    @Before
    public void setUp(){
        driver = DriverFactory.getDriver();
        propertyReader = new PropertyReader();
        paginaLogin = new PaginaLogin(driver);
        driver.get("https://www.amazon.com.br/");


    }

    @AfterAll
    public void tearDown() {

        DriverFactory.quitDriver();
    }


}
