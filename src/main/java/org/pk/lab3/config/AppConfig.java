package org.pk.lab3.config;

import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private static final String CONFIG_FILE = "config.properties";
    private final Properties properties = new Properties();
    private static AppConfig instance;

    private AppConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.out.println("Unable to get: " + CONFIG_FILE);
                return;
            }

            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public String getMainViewPath() {
        return properties.getProperty("main.view.path");
    }

    public String getProductCreateViewPath() {
        return properties.getProperty("product.create.view.path");
    }

    public String getProductDetailsViewPath() {
        return properties.getProperty("product.details.view.path");
    }

    public String getProductListViewPath() {
        return properties.getProperty("product.list.view.path");
    }

    public String getProductApiUrl() {
        return properties.getProperty("product.api.url");
    }
}
