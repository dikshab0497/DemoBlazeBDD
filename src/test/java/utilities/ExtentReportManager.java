package utilities;

import com.aventstack.extentreports.ExtentReports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.net.InetAddress;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static synchronized ExtentReports getExtent() {
        if (extent == null) {
            String reportDir = System.getProperty("extent.report.dir", System.getProperty("user.dir") + "/ExtentReport");
            new File(reportDir).mkdirs();
//            String reportPath = reportDir + "/ExtentReport.html";
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = reportDir + "/ExtentReport_" + timeStamp + ".html";
            
            System.out.println("Extent Report Path: " + reportPath);

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("Execution Report");
            spark.config().setReportName("Automation Test Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Metadata
            extent.setSystemInfo("Executed By", System.getProperty("user.name"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java", System.getProperty("java.version"));
            extent.setSystemInfo("Machine", getHostName());
        }
        return extent;
    }

    public static ExtentTest createTest(String name) {
        ExtentTest test = getExtent().createTest(name);
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

    private static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "Unknown";
        }
    }
}
