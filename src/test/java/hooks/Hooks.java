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
    public void setUp(Scenario scenario) throws Exception {
        loadConfig();
        setupDriver("windows", configProp.getProperty("browser"));
        openApplication();

        ExtentReportManager.createTest(scenario.getName())
                .log(Status.INFO, "Starting scenario: " + scenario.getName());
    }

    @After
    public void tearDownScenario(Scenario scenario) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());

            String path = ScreenshotUtility.takeScreenshot(driver, scenario.getName() + "_" + System.currentTimeMillis());

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
            ExtentReportManager.getTest().log(Status.FAIL, "Error: " + e.getMessage());
        } finally {
            tearDown();  // close browser
        }
    }
}
