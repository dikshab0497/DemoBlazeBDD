package hooks;

import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import testBase.BaseClass;
import utilities.ConfigPropertiesUtility;
import utilities.ExtentReportManager;
import utilities.ScreenshotUtility;

public class Hooks extends BaseClass {

    private static final String SEPARATOR = "================================================";

    @Before(order = 0)
    public void setUp(Scenario scenario) throws Exception {
        logger.info(SEPARATOR);
        logger.info("[SCENARIO] ID      : " + scenario.getId());
        logger.info("[SCENARIO] Name    : " + scenario.getName());
        logger.info("[SCENARIO] Tags    : " + scenario.getSourceTagNames());
        logger.info("[SCENARIO] Start   : " + java.time.LocalDateTime.now());
        logger.info(SEPARATOR);

        String browser = System.getProperty("browser");
        if (browser == null) {
            browser = ConfigPropertiesUtility.getProperty("browser");
        }
        setupDriver("windows", browser);
        openApplication();

        ExtentReportManager.createTest(scenario.getName())
                .log(Status.INFO, "Tags: " + scenario.getSourceTagNames())
                .log(Status.INFO, "Starting scenario: " + scenario.getName());
    }

    @After(order = 0)
    public void tearDownScenario(Scenario scenario) {
        logger.info(SEPARATOR);
        logger.info("[SCENARIO] Name    : " + scenario.getName());
        logger.info("[SCENARIO] End     : " + java.time.LocalDateTime.now());
        logger.info("[SCENARIO] Status  : " + (scenario.isFailed() ? "FAILED" : "PASSED"));
        logger.info(SEPARATOR);

        try {
            String screenshotPath = ScreenshotUtility.takeScreenshot(getDriver(), scenario.getName());
            if (scenario.isFailed()) {
                ExtentReportManager.getTest()
                        .fail("Scenario FAILED: " + scenario.getName())
                        .addScreenCaptureFromPath(screenshotPath);
                logger.error("[SCREENSHOT] Failure screenshot: " + screenshotPath);
            } else {
                ExtentReportManager.getTest()
                        .pass("Scenario PASSED: " + scenario.getName())
                        .addScreenCaptureFromPath(screenshotPath);
                logger.info("[SCREENSHOT] Pass screenshot: " + screenshotPath);
            }
        } catch (Exception e) {
            ExtentReportManager.getTest().fail("Error capturing screenshot: " + e.getMessage());
            logger.error("[ERROR] Screenshot/report error: " + e.getMessage(), e); // ✅ logs full stack trace
        } finally {
            tearDown();
        }
    }
}