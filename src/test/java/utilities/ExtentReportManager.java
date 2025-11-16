package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ExtentTest test;
    private static String reportPath;

    public static ExtentReports getExtent(String testName) {
        if (extent == null) {
            try {
            	
            	String reportDir = System.getProperty("user.dir") + "/reports";
                File dir = new File(reportDir);
                if (!dir.exists()) dir.mkdirs();

                // Fixed name for Jenkins, timestamped for local
                if (System.getenv("JENKINS_HOME") != null) {
                    reportPath = reportDir + "/Test-Report.html";
                } else {
//                    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    reportPath = reportDir + "/Test-Report-" + testName + ".html";
                }

                ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
                spark.config().setReportName("Automation Test Report");
                spark.config().setDocumentTitle("Execution Results");
                spark.config().setOfflineMode(true); // works on Jenkins without CDN

                extent = new ExtentReports();
                extent.attachReporter(spark);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return extent;
    }

    public static ExtentTest createTest(String testName) {
        test = getExtent(testName.replace(" ", "")).createTest(testName);
        return test;
    }

    public static ExtentTest getTest() {
        return test;
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();

            // Open only locally, not on Jenkins
            if (System.getenv("JENKINS_HOME") == null && Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new File(reportPath).toURI());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("ExtentReport generated at: " + reportPath);
            }
        }
    }
}
