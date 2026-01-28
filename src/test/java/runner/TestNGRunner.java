package runner;

import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterSuite;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utilities.ExtentReportManager;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"StepDefinitions", "hooks"},
        plugin = {"pretty", "json:target/cucumber.json"},
        monochrome = true
)
public class TestNGRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterSuite
    public void flushExtentReport() {
        ExtentReportManager.flushReport();  
        // flush once after all parallel tests
    }
}

