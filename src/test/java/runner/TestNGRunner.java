package runner;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utilities.ConfigPropertiesUtility;
import utilities.ExtentReportManager;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"StepDefinitions", "hooks"},
        plugin = {"pretty", "json:target/cucumber.json"},
        monochrome = true
)
public class TestNGRunner extends AbstractTestNGCucumberTests {

    private static final Logger logger = LogManager.getLogger(TestNGRunner.class);

    @BeforeSuite
    public void setCucumberTags() throws IOException {
        logger.info("[RUNNER] ================================================");
        logger.info("[RUNNER] Test Suite Starting...");
        logger.info("[RUNNER] ================================================");

        String tag = System.getProperty("cucumber.filter.tags");
        if (tag == null || tag.trim().isEmpty()) {
            tag = ConfigPropertiesUtility.getProperty("testCase");
            System.setProperty("cucumber.filter.tags", tag);
            logger.info("[RUNNER] Tags loaded from config: " + tag);
        } else {
            logger.info("[RUNNER] Tags passed via system property: " + tag);
        }

        logger.info("[RUNNER] Executing Cucumber Tag: " + System.getProperty("cucumber.filter.tags"));
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        logger.info("[RUNNER] Loading scenarios (parallel execution enabled)");
        return super.scenarios();
    }

    @AfterSuite
    public void flushExtentReport() {
        ExtentReportManager.flushReport();
        logger.info("[RUNNER] ================================================");
        logger.info("[RUNNER] Test Suite Finished. Extent Report flushed.");
        logger.info("[RUNNER] ================================================");
    }
}