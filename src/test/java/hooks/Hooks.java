package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import driver.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Ações que rodam antes e depois de CADA cenário. */
public class Hooks {

    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void antesDoCenario(Scenario cenario) {
        log.info("Iniciando cenário: {}", cenario.getName());
    }

    @After
    public void depoisDoCenario(Scenario cenario) {
        try {
            // Se o cenário falhou, anexa um print da tela ao relatório para facilitar o diagnóstico
            if (cenario.isFailed() && DriverFactory.hasDriver()) {
                byte[] print = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
                cenario.attach(print, "image/png", "Screenshot da falha");
            }
        } catch (WebDriverException e) {
            log.warn("Não foi possível capturar o screenshot: {}", e.getMessage());
        } finally {
            // Sempre fecha o navegador, mesmo que algo acima dê erro
            DriverFactory.quitDriver();
        }
        log.info("Cenário finalizado: {} -> {}", cenario.getName(), cenario.getStatus());
    }
}
