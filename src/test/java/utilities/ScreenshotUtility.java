package utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtility {

    public static String takeScreenshot(WebDriver driver, String screenshotName) {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Directory passed from Jenkins via -Dextent.report.dir
        String reportDir = System.getProperty("extent.report.dir");

        // Local fallback
        if (reportDir == null) {
            reportDir = System.getProperty("user.dir") + "/ExtentReport";
        }

        // Screenshot folder inside the tag folder
        String screenshotDir = reportDir + "/screenshots/";
        new File(screenshotDir).mkdirs();

        String screenshotPath = screenshotDir + screenshotName + "_" + timeStamp + ".png";

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(screenshotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Relative path needed for ExtentReports
        return "screenshots/" + screenshotName + "_" + timeStamp + ".png";
    }
}
