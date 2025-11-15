package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

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
                spark.config().setReportName("Automation Test Report");
                spark.config().setDocumentTitle("Execution Results");

                // DO NOT USE CDN in Jenkins → it breaks CSS
                // spark.config().setResourceCDN(ExtentSparkReporter.CDN.EXTENTREPORTS);

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

            // Inject custom CSS so Jenkins displays report properly
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

    // Utility: embed CSS directly inside HTML <head>
    private static void injectCss(String filePath) {
        try {
            File htmlFile = new File(filePath);
            String html = new String(Files.readAllBytes(htmlFile.toPath()), StandardCharsets.UTF_8);

            String customCss =
                    "body { font-family: 'Segoe UI', Arial, sans-serif; } "
                    + ".status.pass{ background:#e6ffe6 !important; color:#008000 !important;} "
                    + ".status.fail{ background:#ffe6e6 !important; color:#cc0000 !important;} ";

            String styleBlock = "<style>" + customCss + "</style>";

            html = html.replace("</head>", styleBlock + "</head>");

            Files.write(htmlFile.toPath(), html.getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
