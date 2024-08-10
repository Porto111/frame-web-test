package factory;

import config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    private static WebDriverThread webDriverThread = new WebDriverThread();

    public static WebDriver getDriver() {
        if (webDriverThread.getDriver() == null) {
            String browser = Config.getBrowser();
            switch (browser) {
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", Config.getDriverPath());
                    webDriverThread.setDriver(new ChromeDriver());
                    break;
                case "firefox":
                    System.setProperty("webdriver.gecko.driver", Config.getDriverPath());
                    webDriverThread.setDriver(new FirefoxDriver());
                    break;
                default:
                    throw new IllegalArgumentException("Browser não suportado: " + browser);
            }
        }
        return webDriverThread.getDriver();
    }

    public static void quitDriver() {
        webDriverThread.quitDriver();
    }
}
