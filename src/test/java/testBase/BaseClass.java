package testBase;

import java.io.IOException;
import java.net.URL;

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

    private static final String SEPARATOR = "================================================";
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static final Logger logger = LogManager.getLogger(BaseClass.class);

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setupDriver(String os, String browser) throws IOException {
        String executionEnv = ConfigPropertiesUtility.getProperty("execution_env");
        logger.info(SEPARATOR);
        logger.info("[DRIVER] Execution Environment : " + executionEnv);
        logger.info("[DRIVER] OS                    : " + os);
        logger.info("[DRIVER] Browser               : " + browser);
        logger.info(SEPARATOR);

        if (executionEnv.equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setPlatform(Platform.valueOf(os.toUpperCase()));
            capabilities.setBrowserName(browser);
            driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities));
            logger.info("[DRIVER] Remote WebDriver initialised");
        } else if (executionEnv.equalsIgnoreCase("local")) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    driver.set(new ChromeDriver());
                    break;
                case "edge":
                    driver.set(new EdgeDriver());
                    break;
                case "firefox":
                    driver.set(new FirefoxDriver());
                    break;
                default:
                    logger.error("[DRIVER] Invalid browser specified: " + browser);
                    throw new IllegalArgumentException("Invalid browser: " + browser);
            }
            logger.info("[DRIVER] Local WebDriver initialised");
        }

        getDriver().manage().window().maximize();
        logger.info("[DRIVER] Browser launched and maximised: " + browser);
    }

    public static void openApplication() throws IOException {
        String env = System.getProperty("env", "qa").toLowerCase();
        String appURL = ConfigPropertiesUtility.getProperty(env + ".appURL");
        logger.info("[APP] Environment : " + env);
        logger.info("[APP] Opening URL : " + appURL);
        getDriver().get(appURL);
        logger.info("[APP] Application loaded successfully");
    }

    public static void tearDown() {
        if (getDriver() != null) {
            logger.info("[DRIVER] Closing browser...");
            getDriver().quit();
            driver.remove();
            logger.info("[DRIVER] Browser closed and ThreadLocal driver removed");
        } else {
            logger.warn("[DRIVER] tearDown called but driver was already null");
        }
    }

    @AfterSuite
    public void flushExtentReport() {
        logger.info("[REPORT] Flushing Extent Report...");
        ExtentReportManager.flushReport();
        logger.info("[REPORT] Extent Report flushed successfully");
    }
}