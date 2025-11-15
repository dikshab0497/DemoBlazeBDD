package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    private static String reportPath;

    // Initialize ExtentReports
    public static ExtentReports getExtent() {
        if (extent == null) {
            try {
                File reportDir = new File(System.getProperty("user.dir") + "/ExtentReports");
                if (!reportDir.exists()) reportDir.mkdirs();

                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                reportPath = reportDir + "/Test-Report-" + timeStamp + ".html";

                ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
                spark.config().setDocumentTitle("Automation Test Report");
                spark.config().setReportName("Execution Results");
                spark.config().setTheme(Theme.DARK);
                spark.config().setOfflineMode(true);

                extent = new ExtentReports();
                extent.attachReporter(spark);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return extent;
    }

    // Create a test and attach to ThreadLocal
    public static ExtentTest createTest(String testName) {
        ExtentTest t = getExtent().createTest(testName);
        testThread.set(t);
        return t;
    }

    // Get current test
    public static ExtentTest getTest() {
        return testThread.get();
    }

    // Log screenshot easily
    public static void logScreenshot(String message, String screenshotPath) {
        // Relative path to ExtentReports folder
		getTest().fail(message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
    }

    // Flush the report at the end
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
            injectCss(reportPath);

            // Open locally if not running on Jenkins
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

    // Optional CSS injection
    private static void injectCss(String path) {
        try {
            File html = new File(path);
            String content = new String(Files.readAllBytes(html.toPath()), StandardCharsets.UTF_8);

            String css = "<style>"
                    + "body { font-family: Arial, sans-serif; }"
                    + ".status.pass { color: #00b33c !important; }"
                    + ".status.fail { color: #cc0000 !important; }"
                    + "</style>";

            content = content.replace("</head>", css + "</head>");
            Files.write(html.toPath(), content.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ignored) {}
    }
}
