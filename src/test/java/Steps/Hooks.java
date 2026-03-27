package Steps;

import contexto.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ScreenshotUtil;

/**
 * Hooks do Cucumber - executados antes e depois de cada cenário.
 * Utiliza injeção de dependência via PicoContainer.
 */
public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private final TestContext context;

    /**
     * Construtor com injeção de dependência do PicoContainer
     */
    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void setUp(Scenario scenario) {
        logger.info("========================================");
        logger.info("Iniciando cenário: {}", scenario.getName());
        logger.info("Tags: {}", scenario.getSourceTagNames());
        logger.info("========================================");
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.error("Cenário FALHOU: {}", scenario.getName());

            // Captura screenshot em caso de falha
            try {
                byte[] screenshot = ScreenshotUtil.captureScreenshotAsBytes(context.getDriver());
                scenario.attach(screenshot, "image/png", "Screenshot da falha");
                logger.info("Screenshot capturado com sucesso");
            } catch (Exception e) {
                logger.error("Erro ao capturar screenshot: {}", e.getMessage());
            }
        } else {
            logger.info("Cenário PASSOU: {}", scenario.getName());
        }

        // Limpa o contexto e fecha o driver
        context.limparContexto();

        logger.info("========================================");
        logger.info("Finalizando cenário: {}", scenario.getName());
        logger.info("Status: {}", scenario.isFailed() ? "FALHOU" : "PASSOU");
        logger.info("========================================\n");
    }
}


