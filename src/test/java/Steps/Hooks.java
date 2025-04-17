//package Steps;
//
//import factory.DriverFactory;
//import io.cucumber.java.AfterAll;
//import io.cucumber.java.BeforeAll;
//import org.openqa.selenium.WebDriver;
//import pageObjects.PaginaInicial;
//import pageObjects.PaginaLogin;
//import utils.PropertyReader;
//
//public class Hooks {
//    private static WebDriver driver;
//    private static PaginaLogin paginaLogin;
//    private static PaginaInicial paginaInicial;
//    private static PropertyReader propertyReader;
//
//    @BeforeAll
//    public static void setUp() {
//        driver = DriverFactory.getDriver();
//        propertyReader = new PropertyReader();
//        paginaLogin = new PaginaLogin(driver);
//        driver.get("https://web-premio-empreendedor-sabesp.dev.internal.solutis.xyz/");
//    }
//
//    @AfterAll
//    public static void tearDown() {
//        DriverFactory.quitDriver();
//    }
//}

package Steps;

import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
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
    public void setUp() {
        driver = DriverFactory.getDriver();
        propertyReader = new PropertyReader();
        paginaLogin = new PaginaLogin(driver);
        driver.get("https://web-premio-empreendedor-sabesp.dev.internal.solutis.xyz/");
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}


