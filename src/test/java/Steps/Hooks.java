package Steps;

import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import org.openqa.selenium.WebDriver;

public class Hooks {
    private WebDriver driver;

    @BeforeAll
    public void setUp(){
//        getDriver();
        driver.get("https://amazom.com.br");
    }

    @AfterAll
    public void tearDown() {
        DriverFactory.quitDriver();
    }

//    public Object getDriver() {
//        return driver;
//    }

    public void setDriver(Object driver) {
        this.driver = (WebDriver) driver;
    }
}
