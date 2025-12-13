package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.net.InetAddress;

public class ExtentReportManager {

    private static ExtentReports extent; // SINGLETON now
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static String reportPath; // Only one report path needed

    public static synchronized ExtentReports getExtent() {

        if (extent == null) {

            // Folder passed from Jenkins: -Dextent.report.dir
            String reportDir = System.getProperty("extent.report.dir");

            // Fallback for local run
            if (reportDir == null) {
                reportDir = System.getProperty("user.dir") + "/ExtentReport";
            }

            new File(reportDir).mkdirs();

            reportPath = reportDir + "/ExtentReport.html"; // single file for all tests

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("Automation Test Report");
            spark.config().setDocumentTitle("Execution Results");
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setEncoding("utf-8");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Add metadata
            extent.setSystemInfo("Executed By", System.getProperty("user.name"));
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Environment", System.getProperty("env", "LOCAL"));
            extent.setSystemInfo("Machine", getHostName());
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

    public static synchronized void flushReport() { // flush only once
        if (extent != null) {
            extent.flush();
        }
    }

    public static String getReportPath() {
        return reportPath;
    }

    private static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "Unknown Host";
        }
    }
}
