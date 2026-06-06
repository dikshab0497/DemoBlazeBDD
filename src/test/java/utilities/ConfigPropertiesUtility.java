package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigPropertiesUtility {
	
public static Properties configProp;
	
	// Load config.properties

	 public static void loadConfig() throws IOException {
	        if (configProp == null) {
	            configProp = new Properties();
	            FileInputStream fis = new FileInputStream(".//src//test//resources//config.properties");
	            configProp.load(fis);
	        }
	    }

	 public static String getProperty(String key) throws IOException {
		    loadConfig();
		    return configProp.getProperty(key);
		}
}
