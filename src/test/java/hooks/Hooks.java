package hooks;

import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import testBase.BaseClass;
import utilities.ExtentReportManager;
import utilities.ScreenshotUtility;

public class Hooks extends BaseClass {

    @Before
    public void beforeScenario(Scenario scenario) throws Exception {
        // ✅ Load config and initialize WebDriver
        loadConfig();
        setupDriver("windows", configProp.getProperty("browser")); // adjust OS if needed
        openApplication();

        // ✅ Create ExtentTest for this scenario
        ExtentReportManager.createTest(scenario.getName());
        ExtentReportManager.getTest().log(Status.INFO, "Starting scenario: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        try {
            // ⭐ Capture screenshot for Cucumber report
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot");

            // ⭐ Save screenshot for ExtentReport
            String path = ScreenshotUtility.takeScreenshot(driver, scenario.getName());

            if (scenario.isFailed()) {
                ExtentReportManager.getTest()
                        .fail("Scenario failed: " + scenario.getName())
                        .addScreenCaptureFromPath(path);
            } else {
                ExtentReportManager.getTest()
                        .pass("Scenario passed: " + scenario.getName())
                        .addScreenCaptureFromPath(path);
            }

        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Error in Hooks: " + e.getMessage());
        } finally {
            // Close browser
            tearDown();

            // ⭐ Flush report
            if (System.getenv("JENKINS_HOME") == null) {
                // Local: flush and open HTML
                ExtentReportManager.flushReport();
            } else {
                // Jenkins: flush report but do NOT open browser
                ExtentReportManager.getExtent().flush();
            }
        }
    }
}
