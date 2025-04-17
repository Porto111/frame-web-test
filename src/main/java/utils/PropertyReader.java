package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyReader {
    private static final Logger LOGGER = Logger.getLogger(PropertyReader.class.getName());
    private Properties properties;

    public PropertyReader() {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading properties file", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}