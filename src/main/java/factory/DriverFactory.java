package factory;

import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory responsável por criar e gerenciar instâncias do WebDriver.
 * Utiliza ThreadLocal para suporte a execução paralela de testes.
 */
public class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    /**
     * ThreadLocal garante que cada thread tenha sua própria instância do WebDriver.
     * Essencial para execução paralela de testes.
     */
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Obtém a instância do WebDriver para a thread atual.
     * Cria nova instância se não existir.
     */
    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            logger.info("Criando nova instância do WebDriver");
            WebDriver driver = createDriver();
            driverThreadLocal.set(driver);
        }
        return driverThreadLocal.get();
    }

    /**
     * Cria instância do WebDriver baseado na configuração.
     */
    private static WebDriver createDriver() {
        String browser = Config.getBrowser().toLowerCase();
        boolean headless = Config.isHeadless();

        logger.info("Inicializando driver para browser: {} (headless: {})", browser, headless);

        return switch (browser) {
            case "chrome" -> createChromeDriver(headless);
            case "firefox" -> createFirefoxDriver(headless);
            case "edge" -> createEdgeDriver(headless);
            default -> throw new IllegalArgumentException(
                    "Browser não suportado: " + browser + ". Use: chrome, firefox ou edge");
        };
    }

    /**
     * Cria ChromeDriver com WebDriverManager
     */
    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        return new ChromeDriver(options);
    }

    /**
     * Cria FirefoxDriver com WebDriverManager
     */
    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (headless) {
            options.addArguments("--headless");
        }

        WebDriver driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * Cria EdgeDriver com WebDriverManager
     */
    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");

        if (headless) {
            options.addArguments("--headless=new");
        }

        return new EdgeDriver(options);
    }

    /**
     * Encerra o WebDriver da thread atual
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            logger.info("Encerrando instância do WebDriver");
            try {
                driver.quit();
            } catch (Exception e) {
                logger.error("Erro ao encerrar WebDriver: {}", e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    /**
     * Verifica se existe instância ativa do WebDriver
     */
    public static boolean hasActiveDriver() {
        return driverThreadLocal.get() != null;
    }
}