package utilities;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static synchronized ExtentReports getExtent() throws IOException {
        if (extent == null) {
            String reportDir = System.getProperty("extent.report.dir",
                    System.getProperty("user.dir") + "/ExtentReport");
            new File(reportDir).mkdirs();

            String timeStamp  = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String testCase   = ConfigPropertiesUtility.getProperty("testCase").replace("@", "");
            String reportPath = reportDir + "/" + testCase + "_" + timeStamp + ".html";

            logger.info("[REPORT] Report directory : " + reportDir);
            logger.info("[REPORT] Report file      : " + reportPath);

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("Execution Report");
            spark.config().setReportName("Automation Test Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            String executedBy = System.getProperty("user.name");
            String os         = System.getProperty("os.name");
            String java       = System.getProperty("java.version");
            String machine    = getHostName();

            extent.setSystemInfo("Executed By", executedBy);
            extent.setSystemInfo("OS",          os);
            extent.setSystemInfo("Java",        java);
            extent.setSystemInfo("Machine",     machine);

            logger.info("[REPORT] System Info — Executed By: " + executedBy
                    + " | OS: " + os
                    + " | Java: " + java
                    + " | Machine: " + machine);
            logger.info("[REPORT] ExtentReports initialised successfully");
        }
        return extent;
    }

    public static ExtentTest createTest(String name) throws IOException {
        logger.info("[REPORT] Creating test node: " + name);
        ExtentTest test = getExtent().createTest(name);
        extentTest.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void flushReport() {
        if (extent != null) {
            logger.info("[REPORT] Flushing Extent Report...");
            extent.flush();
            logger.info("[REPORT] Extent Report flushed successfully");
        } else {
            logger.warn("[REPORT] flushReport called but ExtentReports instance was null");
        }
    }

    private static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            logger.warn("[REPORT] Could not retrieve hostname: " + e.getMessage());
            return "Unknown";
        }
    }
}