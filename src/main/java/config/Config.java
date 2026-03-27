package config;

import utils.PropertyReader;

/**
 * Classe central de configuração do framework.
 * Fornece acesso padronizado a todas as configurações.
 */
public class Config {

    private static final PropertyReader reader = new PropertyReader();

    private Config() {
        // Classe utilitária - não instanciar
    }

    // ==================== Configurações de Ambiente ====================

    public static String getEnvironment() {
        return reader.getEnvironment();
    }

    public static String getBaseUrl() {
        return reader.getProperty("app.base.url");
    }

    // ==================== Configurações de Browser ====================

    public static String getBrowser() {
        return reader.getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        return reader.getBooleanProperty("browser.headless", true);
    }

    public static int getImplicitWait() {
        return reader.getIntProperty("browser.implicit.wait", 10);
    }

    public static int getPageLoadTimeout() {
        return reader.getIntProperty("browser.page.load.timeout", 30);
    }

    // ==================== Configurações de Timeouts ====================

    public static int getExplicitWait() {
        return reader.getIntProperty("wait.explicit.timeout", 10);
    }

    public static int getPollingInterval() {
        return reader.getIntProperty("wait.polling.interval", 500);
    }

    // ==================== Credenciais de Teste ====================

    public static String getLoginUsername() {
        return reader.getProperty("login.username");
    }

    public static String getLoginPassword() {
        return reader.getProperty("login.password");
    }

    public static String getInvalidUsername() {
        return reader.getProperty("login.invalid.username", "usuario_invalido");
    }

    public static String getInvalidPassword() {
        return reader.getProperty("login.invalid.password", "senha_invalida");
    }

    // ==================== Configurações de Relatório ====================

    public static boolean isScreenshotOnFailure() {
        return reader.getBooleanProperty("report.screenshot.on.failure", true);
    }

    public static String getReportPath() {
        return reader.getProperty("report.path", "target/cucumber-reports");
    }
}