package utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtility {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtility.class);

    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String reportDir = System.getProperty("extent.report.dir");
        if (reportDir == null) {
            reportDir = System.getProperty("user.dir") + "/ExtentReport";
            logger.info("[SCREENSHOT] No report dir from system property — using default: " + reportDir);
        } else {
            logger.info("[SCREENSHOT] Report dir from system property: " + reportDir);
        }

        String screenshotDir  = reportDir + "/screenshots/";
        String screenshotPath = screenshotDir + screenshotName + "_" + timeStamp + ".png";
        String relativePath   = "screenshots/" + screenshotName + "_" + timeStamp + ".png";

        new File(screenshotDir).mkdirs();

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(screenshotPath));
            logger.info("[SCREENSHOT] Captured: " + screenshotPath);
        } catch (IOException e) {
            logger.error("[SCREENSHOT] Failed to capture screenshot '" + screenshotName + "': " + e.getMessage(), e); // ✅ replaced e.printStackTrace()
        }

        return relativePath;
    }
}