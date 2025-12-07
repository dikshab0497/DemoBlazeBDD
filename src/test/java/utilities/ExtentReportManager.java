package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static String reportPath;

    public static ExtentReports getExtent() {
        if (extent == null) {
            // Use build-specific folder from Jenkins or fallback
            String reportDir = System.getProperty("extent.report.dir", System.getProperty("user.dir") + "/reports");
            new File(reportDir).mkdirs();
            
            if (System.getenv("JENKINS_HOME") != null) {
                // Unique report per thread in Jenkins
                reportPath = reportDir + "/Test-Report-" + Thread.currentThread().getId() + ".html";
            } else {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                reportPath = reportDir + "/Test-Report-" + timeStamp + ".html";
            }


            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("Automation Test Report");
            spark.config().setDocumentTitle("Execution Results");
            spark.config().setOfflineMode(true);

            extent = new ExtentReports();
            extent.attachReporter(spark);

            System.out.println("ExtentReport will be generated at: " + reportPath);
        }
        return extent;
    }

    public static ExtentTest createTest(String testName) {
        ExtentTest test = getExtent().createTest(testName);
        extentTest.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
