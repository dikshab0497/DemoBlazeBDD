package runner;

import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterSuite;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.java.Scenario;
import hooks.Hooks;
import utilities.ExtentReportManager;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"StepDefinitions", "hooks"},
        plugin = {
                "pretty",
                "json:target/cucumber.json"
        },
        monochrome = true
)
public class TestNGRunner extends AbstractTestNGCucumberTests {

    // Enable parallel execution
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    // Flush Extent report once after all scenarios finish
    @AfterSuite
    public void flushExtentReport() {
        ExtentReportManager.flushReport();
    }
}
