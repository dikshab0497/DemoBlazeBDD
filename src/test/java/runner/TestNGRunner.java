//package runner;
//
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.AfterSuite;
//
//import io.cucumber.testng.AbstractTestNGCucumberTests;
//import io.cucumber.testng.CucumberOptions;
//import utilities.ExtentReportManager;
//
//@CucumberOptions(
//        features = "src/test/resources/features",
//        glue = {"StepDefinitions", "hooks"},
//        plugin = {"pretty", "json:target/cucumber.json"},
//        monochrome = true,
//        tags = "@Smoke"
//)
//public class TestNGRunner extends AbstractTestNGCucumberTests {
//
//    @Override
//    @DataProvider(parallel = true)
//    public Object[][] scenarios() {
//        return super.scenarios();
//    }
//
//    @AfterSuite
//    public void flushExtentReport() {
//        ExtentReportManager.flushReport();  
//        // flush once after all parallel tests
//    }
//}
//
package runner;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterSuite;

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

	public static Properties configProp;
	
	// Load config.properties
    public static void loadConfig() throws IOException {
        if (configProp == null) {
            configProp = new Properties();
            FileInputStream fis = new FileInputStream(".//src//test//resources//config.properties");
            configProp.load(fis);
        }
    }
	
    @BeforeSuite
    public void setCucumberTags() throws IOException {
        String tag = System.getProperty("cucumber.filter.tags");

        if (tag == null || tag.trim().isEmpty()) {
            tag = ConfigPropertiesUtility.getProperty("testCase");
            System.setProperty("cucumber.filter.tags", tag);
        }

        System.out.println("Executing Cucumber Tag: " + System.getProperty("cucumber.filter.tags"));
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterSuite
    public void flushExtentReport() {
        ExtentReportManager.flushReport();
    }
}
