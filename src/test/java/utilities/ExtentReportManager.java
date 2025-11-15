package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ResourceCDN;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ExtentTest test;
    private static String reportPath;

    public static ExtentReports getExtent() {
        if (extent == null) {
            try {
                // Create folder if missing
                File reportDir = new File(System.getProperty("user.dir") + "/ExtentReports");
                if (!reportDir.exists()) reportDir.mkdirs();

                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                reportPath = reportDir + "/Test-Report-" + timeStamp + ".html";

                // Spark reporter
                ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
                spark.config().setReportName("Automation Test Report");
                spark.config().setDocumentTitle("Execution Results");

//                spark.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);

                // Initialize
                extent = new ExtentReports();
                extent.attachReporter(spark);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return extent;
    }

    public static ExtentTest createTest(String testName) {
        test = getExtent().createTest(testName);
        return test;
    }

    public static ExtentTest getTest() {
        return test;
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();

            // Open only locally, never in Jenkins
            if (System.getenv("JENKINS_HOME") == null) {
                try {
                    Desktop.getDesktop().browse(new File(reportPath).toURI());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
