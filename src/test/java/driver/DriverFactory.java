package driver;

import config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Cria e encerra o navegador.
 *
 * O navegador fica guardado em um ThreadLocal: cada thread de teste tem o seu próprio,
 * o que permite rodar cenários em paralelo no futuro sem um atrapalhar o outro.
 * O driver do navegador é baixado automaticamente pelo Selenium Manager (já incluso no Selenium).
 */
public final class DriverFactory {

    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    /** Devolve o navegador atual; se ainda não existir, abre um novo. */
    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            DRIVER.set(criarDriver());
        }
        return DRIVER.get();
    }

    public static boolean hasDriver() {
        return DRIVER.get() != null;
    }

    /** Fecha o navegador do cenário atual. */
    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                DRIVER.remove();
            }
        }
    }

    private static WebDriver criarDriver() {
        String browser = Config.browser();
        boolean headless = Config.headless();
        log.info("Abrindo navegador: {} (headless={})", browser, headless);

        WebDriver driver = switch (browser) {
            case "chrome" -> criarChrome(headless);
            case "firefox" -> criarFirefox(headless);
            case "edge" -> criarEdge(headless);
            default -> throw new IllegalArgumentException(
                    "Navegador não suportado: " + browser + ". Use chrome, firefox ou edge.");
        };

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        return driver;
    }

    private static WebDriver criarChrome(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080", "--disable-notifications");
        if (headless) {
            // --no-sandbox e --disable-dev-shm-usage evitam falhas comuns em servidores de CI
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        }

        // Desliga os pop-ups de senha do Chrome, que cobrem a tela e atrapalham os testes de login
        Map<String, Object> preferencias = new HashMap<>();
        preferencias.put("credentials_enable_service", false);
        preferencias.put("profile.password_manager_enabled", false);
        preferencias.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", preferencias);

        return new ChromeDriver(options);
    }

    private static WebDriver criarFirefox(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920", "--height=1080");
        if (headless) {
            options.addArguments("-headless");
        }
        return new FirefoxDriver(options);
    }

    private static WebDriver criarEdge(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--window-size=1920,1080");
        if (headless) {
            options.addArguments("--headless=new");
        }
        return new EdgeDriver(options);
    }
}

