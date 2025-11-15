package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
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
    private static ExtentTest test;
    private static String reportPath;

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

                // ⭐ Offline Mode → Copies CSS + JS locally
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

            // ⭐ Fix CSS for Jenkins: inject basic CSS manually
            injectCss(reportPath);

            if (System.getenv("JENKINS_HOME") == null) {
                try {
                    Desktop.getDesktop().browse(new File(reportPath).toURI());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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
