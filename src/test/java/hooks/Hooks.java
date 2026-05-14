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
        String browser = System.getProperty("Browser");
        if(browser == null) {
        	browser = configProp.getProperty("browser");
        }
        setupDriver("windows",browser);
        openApplication();

        ExtentReportManager.createTest(scenario.getName())
                .log(Status.INFO, "Starting scenario: " + scenario.getName());
    }

    @After
    public void tearDownScenario(Scenario scenario) {
        try {
            String screenshotPath = ScreenshotUtility.takeScreenshot(driver, scenario.getName());
            if (scenario.isFailed()) {
                ExtentReportManager.getTest().fail("Scenario failed").addScreenCaptureFromPath(screenshotPath);
            } else {
                ExtentReportManager.getTest().pass("Scenario passed").addScreenCaptureFromPath(screenshotPath);
            }
        } catch (Exception e) {
            ExtentReportManager.getTest().fail("Error: " + e.getMessage());
        } finally {
            tearDown(); // close browser
            // DO NOT flush here
        }
    }

}
