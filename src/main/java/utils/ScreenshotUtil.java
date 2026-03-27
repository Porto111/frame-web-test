package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitária para captura de screenshots.
 * Fornece métodos para capturar screenshots em diferentes formatos.
 */
public class ScreenshotUtil {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_DIR = "target/screenshots";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private ScreenshotUtil() {
        // Classe utilitária - não instanciar
    }

    /**
     * Captura um screenshot e retorna como array de bytes.
     * Útil para anexar ao relatório do Cucumber.
     *
     * @param driver WebDriver instance
     * @return byte array do screenshot em formato PNG
     */
    public static byte[] captureScreenshotAsBytes(WebDriver driver) {
        if (driver == null) {
            logger.warn("WebDriver é nulo. Não foi possível capturar screenshot.");
            return new byte[0];
        }

        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Erro ao capturar screenshot: {}", e.getMessage());
            return new byte[0];
        }
    }

    /**
     * Captura um screenshot e salva em arquivo.
     *
     * @param driver WebDriver instance
     * @param fileName nome do arquivo (sem extensão)
     * @return Path do arquivo salvo
     */
    public static Path captureScreenshotAsFile(WebDriver driver, String fileName) {
        if (driver == null) {
            logger.warn("WebDriver é nulo. Não foi possível capturar screenshot.");
            return null;
        }

        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path destination = createScreenshotPath(fileName);
            Files.copy(screenshot.toPath(), destination);
            logger.info("Screenshot salvo em: {}", destination);
            return destination;
        } catch (Exception e) {
            logger.error("Erro ao salvar screenshot: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Captura um screenshot com timestamp no nome do arquivo.
     *
     * @param driver WebDriver instance
     * @param scenarioName nome do cenário para identificação
     * @return Path do arquivo salvo
     */
    public static Path captureScreenshotWithTimestamp(WebDriver driver, String scenarioName) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String fileName = scenarioName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp;
        return captureScreenshotAsFile(driver, fileName);
    }

    /**
     * Cria o diretório de screenshots se não existir.
     */
    private static Path createScreenshotPath(String fileName) throws IOException {
        Path dirPath = Paths.get(SCREENSHOT_DIR);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        return dirPath.resolve(fileName + ".png");
    }
}
