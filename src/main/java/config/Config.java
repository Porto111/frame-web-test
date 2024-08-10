package config;

import utils.PropertyReader;

public class Config {
    private static PropertyReader reader = new PropertyReader();

    public static String getBrowser() {
        return reader.getProperty("browser");
    }

    public static String getDriverPath() {
        return reader.getProperty("driverPath");
    }

    public static String getLoginUsername() {
        return reader.getProperty("login.username");
    }

    public static String getLoginPassword() {
        return reader.getProperty("login.password");
    }
}

