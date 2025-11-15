package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

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

                // Create ExtentReports directory
                File reportDir = new File(System.getProperty("user.dir") + "/ExtentReports");
                if (!reportDir.exists()) reportDir.mkdirs();

                // Create timestamped file
                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                reportPath = reportDir + "/Test-Report-" + timeStamp + ".html";

                // Spark reporter with offline support
                ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

                spark.config().setReportName("Automation Test Report");
                spark.config().setDocumentTitle("Execution Results");
                spark.config().setTheme(Theme.DARK);

                // ⭐ MUST FOR JENKINS: Copies CSS + JS locally
                spark.config().setOfflineMode(true);

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

            // ⭐ Do NOT auto-open report on Jenkins
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
