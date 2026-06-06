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
import org.testng.annotations.AfterSuite;

import utilities.ConfigPropertiesUtility;
import utilities.ExtentReportManager;

public class BaseClass {

//    public static WebDriver driver;
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();// for parellel executions
    public static Logger logger;
    
    
    public static WebDriver getDriver() {
        return driver.get();
    }

    // Setup WebDriver (local/remote)
    public static void setupDriver(String os, String browser) throws IOException {
        logger = LogManager.getLogger(BaseClass.class);

        if (ConfigPropertiesUtility.getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setPlatform(Platform.valueOf(os.toUpperCase()));
            capabilities.setBrowserName(browser);
//            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
            driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities));

        } else if (ConfigPropertiesUtility.getProperty("execution_env").equalsIgnoreCase("local")) {
            switch (browser.toLowerCase()) {
                case "chrome":
                	driver.set(new ChromeDriver());
                    break;
                case "edge":
                	driver.set(new EdgeDriver());
//                    driver = new EdgeDriver();
                    break;
                case "firefox":
                	driver.set(new FirefoxDriver());
//                    driver = new FirefoxDriver();
                default:
                    throw new IllegalArgumentException("Invalid browser: " + browser);
            }
        }

//        driver.manage().window().maximize();
        getDriver().manage().window().maximize();
    }

    // Open application URL
    public static void openApplication() throws IOException {
    	String env = System.getProperty("env", "qa").toLowerCase(); // "dev", "qa", or "uat"
//        driver.get(configProp.getProperty(env +".appURL"));
        getDriver().get(ConfigPropertiesUtility.getProperty(env +".appURL"));
    }

    // Close browser
    public static void tearDown() {
        if (getDriver() != null) {
//            driver.quit();
            getDriver().quit();
            driver.remove();
        }
    }

//     ⭐ Flush ExtentReport after all tests
//    
    @AfterSuite
    public void flushExtentReport() {
        ExtentReportManager.flushReport();
    }
}
