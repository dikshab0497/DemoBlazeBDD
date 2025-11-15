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

        // Save screenshots inside reports folder for Jenkins
        String screenshotDir = System.getProperty("user.dir") + "/reports/screenshots/";
        String screenshotPath = screenshotDir + screenshotName + "_" + timeStamp + ".png";

        try {
            File dir = new File(screenshotDir);
            if (!dir.exists()) dir.mkdirs();

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(screenshotPath));

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return relative path for ExtentReports embedding
        return "screenshots/" + screenshotName + "_" + timeStamp + ".png";
    }
}
