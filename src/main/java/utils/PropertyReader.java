package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Leitor de propriedades com suporte a múltiplos ambientes.
 * Carrega configurações baseadas na propriedade 'environment'.
 */
public class PropertyReader {

    private static final Logger logger = LoggerFactory.getLogger(PropertyReader.class);
    private static final String DEFAULT_ENV = "dev";

    private final Properties properties;
    private final String environment;

    public PropertyReader() {
        this.properties = new Properties();
        this.environment = System.getProperty("env", DEFAULT_ENV);
        loadProperties();
    }

    /**
     * Carrega propriedades do arquivo de configuração
     */
    private void loadProperties() {
        String configFile = String.format("config-%s.properties", environment);

        // Tenta carregar arquivo específico do ambiente
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFile)) {
            if (input != null) {
                properties.load(input);
                logger.info("Configurações carregadas do ambiente: {}", environment);
            } else {
                // Fallback para config.properties padrão
                loadDefaultConfig();
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar arquivo de configuração: {}", configFile, e);
            loadDefaultConfig();
        }

        // Sobrescreve com variáveis de ambiente do sistema
        overrideWithSystemProperties();
    }

    /**
     * Carrega configuração padrão
     */
    private void loadDefaultConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
                logger.info("Configurações padrão carregadas");
            } else {
                logger.error("Arquivo config.properties não encontrado");
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar config.properties", e);
        }
    }

    /**
     * Sobrescreve propriedades com variáveis de ambiente
     */
    private void overrideWithSystemProperties() {
        properties.stringPropertyNames().forEach(key -> {
            String systemValue = System.getProperty(key);
            if (systemValue != null) {
                properties.setProperty(key, systemValue);
                logger.debug("Propriedade '{}' sobrescrita: {}", key, systemValue);
            }
        });
    }

    /**
     * Obtém propriedade como String
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Propriedade não encontrada: {}", key);
        }
        return value;
    }

    /**
     * Obtém propriedade com valor padrão
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Obtém propriedade como boolean
     */
    public boolean getBooleanProperty(String key, boolean b) {
        String value = getProperty(key);
        return Boolean.parseBoolean(value);
    }

    /**
     * Obtém propriedade como int
     */
    public int getIntProperty(String key, int defaultValue) {
        try {
            String value = getProperty(key);
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            logger.warn("Valor inválido para propriedade {}: {}", key, getProperty(key));
            return defaultValue;
        }
    }

    /**
     * Retorna ambiente atual
     */
    public String getEnvironment() {
        return environment;
    }
}