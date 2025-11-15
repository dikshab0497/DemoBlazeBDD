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
	    String reportDir = System.getProperty("user.dir") + "/ExtentReports/screenshots/";
	    String screenshotPath = reportDir + screenshotName + "_" + timeStamp + ".png";

	    try {
	        File dir = new File(reportDir);
	        if (!dir.exists()) dir.mkdirs();

	        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        FileUtils.copyFile(src, new File(screenshotPath));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Return relative path from the report folder
	    return "screenshots/" + screenshotName + "_" + timeStamp + ".png";
	}

}
