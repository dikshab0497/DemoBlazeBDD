package testBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseClass {

    public static WebDriver driver;
    public static Properties configProp;
    public static Logger logger;

    // Load config.properties
    public static void loadConfig() throws IOException {
        if (configProp == null) {
            configProp = new Properties();
            FileInputStream fis = new FileInputStream(".//src//test//resources//config.properties");
            configProp.load(fis);
        }
    }

    // Setup WebDriver (local/remote)
    public static void setupDriver(String os, String browser) throws IOException {
        loadConfig();
        logger = LogManager.getLogger(BaseClass.class);

        if (configProp.getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setPlatform(Platform.valueOf(os.toUpperCase()));
            capabilities.setBrowserName(browser);
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

        } else if (configProp.getProperty("execution_env").equalsIgnoreCase("local")) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid browser: " + browser);
            }
        }

        driver.manage().window().maximize();
    }

    // Open application URL
    public static void openApplication() {
        driver.get(configProp.getProperty("appURL"));
    }

    // Close browser
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
