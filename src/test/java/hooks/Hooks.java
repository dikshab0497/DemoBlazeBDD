package hooks;

import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import testBase.BaseClass;
import utilities.ConfigPropertiesUtility;
import utilities.ExtentReportManager;
import utilities.ScreenshotUtility;

public class Hooks extends BaseClass {

    @Before
    public void setUp(Scenario scenario) throws Exception {
        String browser = System.getProperty("browser");
        if(browser == null) {
        	browser = ConfigPropertiesUtility.getProperty("browser");
        }
        setupDriver("windows",browser);
        openApplication();

        ExtentReportManager.createTest(scenario.getName())
                .log(Status.INFO, "Starting scenario: " + scenario.getName());
    }

    @After
    public void tearDownScenario(Scenario scenario) {
        try {
            String screenshotPath = ScreenshotUtility.takeScreenshot(getDriver(), scenario.getName());
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
