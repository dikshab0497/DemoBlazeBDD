package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeProfileManagerUtility {

    private static final Logger logger = LogManager.getLogger(ChromeProfileManagerUtility.class);
    private static final String SOURCE_PROFILE = "C:\\Users\\Diksha\\selenium-profile";

    public static WebDriver createChromeWithClonedAdBlockProfile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String tempProfilePath = "C:\\Users\\Diksha\\selenium-temp-profile-" + timestamp;

        Path source = Paths.get(SOURCE_PROFILE);
        Path target = Paths.get(tempProfilePath);

        logger.info("[CHROME-PROFILE] Source profile : " + SOURCE_PROFILE);
        logger.info("[CHROME-PROFILE] Temp profile   : " + tempProfilePath);

        if (!Files.exists(source)) {
            logger.error("[CHROME-PROFILE] Source Chrome profile not found: " + SOURCE_PROFILE);
            throw new IOException("Source Chrome profile not found at: " + SOURCE_PROFILE);
        }

        logger.info("[CHROME-PROFILE] Copying profile to temp directory...");
        Files.createDirectories(target);
        copyDirectory(source, target);
        logger.info("[CHROME-PROFILE] Profile copy complete");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=" + tempProfilePath);
        options.addArguments("--remote-allow-origins=*");

        logger.info("[CHROME-PROFILE] Launching Chrome with cloned AdBlock profile...");
        WebDriver driver = new ChromeDriver(options);
        logger.info("[CHROME-PROFILE] Chrome launched successfully with cloned profile");

        return driver;
    }

    private static void copyDirectory(Path source, Path target) throws IOException {
        Files.walk(source).forEach(path -> {
            try {
                Path relative    = source.relativize(path);
                Path destination = target.resolve(relative);
                if (Files.isDirectory(path)) {
                    Files.createDirectories(destination);
                } else {
                    Files.copy(path, destination, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                logger.error("[CHROME-PROFILE] Failed copying: " + path + " | " + e.getMessage()); // ✅ replaced System.err
            }
        });
    }
}