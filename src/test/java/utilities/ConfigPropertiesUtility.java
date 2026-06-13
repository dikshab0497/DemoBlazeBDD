package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigPropertiesUtility {

    private static final Logger logger = LogManager.getLogger(ConfigPropertiesUtility.class);
    private static final String CONFIG_PATH = ".//src//test//resources//config.properties";

    private static Properties configProp;

    public static void loadConfig() throws IOException {
        if (configProp == null) {
            logger.info("[CONFIG] Loading config from: " + CONFIG_PATH);
            configProp = new Properties();
            try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
                configProp.load(fis);
            }
            logger.info("[CONFIG] Config loaded successfully. Total properties: " + configProp.size());
        }
    }

    public static String getProperty(String key) throws IOException {
        loadConfig();
        String value = configProp.getProperty(key);
        if (value == null) {
            logger.warn("[CONFIG] Property not found for key: " + key);
        }
        return value;
    }
}