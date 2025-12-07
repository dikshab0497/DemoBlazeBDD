package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.net.InetAddress;

public class ExtentReportManager {

    private static ThreadLocal<ExtentReports> extent = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static ThreadLocal<String> reportPath = new ThreadLocal<>();

    public static ExtentReports getExtent() {

        if (extent.get() == null) {

            // Folder passed from Jenkins: -Dextent.report.dir
            String reportDir = System.getProperty("extent.report.dir");

            // Fallback for local run
            if (reportDir == null) {
                reportDir = System.getProperty("user.dir") + "/reports";
            }

            new File(reportDir).mkdirs();

            // Jenkins expects this specific filename
            String finalPath = reportDir + "/Test-Report.html";
            reportPath.set(finalPath);

            ExtentSparkReporter spark = new ExtentSparkReporter(finalPath);
            spark.config().setReportName("Automation Test Report");
            spark.config().setDocumentTitle("Execution Results");
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setEncoding("utf-8");
            spark.config().setCss(".badge { font-size: 14px }");

            ExtentReports extentReport = new ExtentReports();
            extentReport.attachReporter(spark);

            // Add metadata
            extentReport.setSystemInfo("Executed By", System.getProperty("user.name"));
            extentReport.setSystemInfo("Operating System", System.getProperty("os.name"));
            extentReport.setSystemInfo("Java Version", System.getProperty("java.version"));
            extentReport.setSystemInfo("Environment", System.getProperty("env", "LOCAL"));
            extentReport.setSystemInfo("Machine", getHostName());

            extent.set(extentReport);
        }

        return extent.get();
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
        if (extent.get() != null) {
            extent.get().flush();
        }
    }

    public static String getReportPath() {
        return reportPath.get();
    }

    private static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "Unknown Host";
        }
    }
}
