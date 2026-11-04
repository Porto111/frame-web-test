package config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Lê as configurações do projeto (config.properties).
 *
 * Regra de prioridade: o que for passado na linha de comando (-Dchave=valor)
 * vence o que está no arquivo. Assim o mesmo código roda local e no CI.
 */
public final class Config {

    private static final Properties ARQUIVO = carregarArquivo();

    private Config() {
    }

    public static String baseUrl() {
        return get("base.url");
    }

    public static String browser() {
        return get("browser").toLowerCase();
    }

    public static boolean headless() {
        return Boolean.parseBoolean(get("headless"));
    }

    public static int timeoutSeconds() {
        return Integer.parseInt(get("timeout.seconds"));
    }

    private static String get(String chave) {
        String valor = System.getProperty(chave, ARQUIVO.getProperty(chave));
        if (valor == null) {
            // Falha rápida: melhor parar aqui com uma mensagem clara do que errar lá na frente
            throw new IllegalStateException("Configuração obrigatória não encontrada: " + chave);
        }
        return valor.trim();
    }

    private static Properties carregarArquivo() {
        Properties propriedades = new Properties();
        try (InputStream entrada = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (entrada == null) {
                throw new IllegalStateException("Arquivo config.properties não encontrado em src/test/resources");
            }
            propriedades.load(new InputStreamReader(entrada, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao ler o config.properties", e);
        }
        return propriedades;
    }
}
