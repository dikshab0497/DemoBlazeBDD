package StepDefinitions;

import com.aventstack.extentreports.Status;

import io.cucumber.java.en.Given;
import pages.LoginPage;
import testBase.BaseClass;
import utilities.ConfigPropertiesUtility;
import utilities.ExtentReportManager;
import utilities.ScreenshotUtility;

public class LoginStepDefinition extends BaseClass {

    LoginPage loginPage;

    @Given("user enters username and password")
    public void enterLoginCredential() throws Exception {
        logger.info("[LOGIN-STEP] Entering username and password...");
        loginPage = new LoginPage(getDriver());
        try {
            String username = ConfigPropertiesUtility.getProperty("qa.userName");
            String password = ConfigPropertiesUtility.getProperty("qa.password");

            logger.info("[LOGIN-STEP] Username: " + username);
            logger.info("[LOGIN-STEP] Password: ********");

            loginPage.enterLoginCredentials(username, password);

            ExtentReportManager.getTest().log(Status.PASS, "Entered username and password");
            logger.info("[LOGIN-STEP] Credentials entered successfully ✅");
        } catch (Exception e) {
            logger.error("[LOGIN-STEP] Failed to enter login credentials: " + e.getMessage(), e);
            String path = ScreenshotUtility.takeScreenshot(getDriver(), "LoginCredentialsFail");
            ExtentReportManager.getTest()
                    .log(Status.FAIL, "❌ Failed to enter login credentials: " + e.getMessage())
                    .addScreenCaptureFromPath(path);
            throw e;
        }
    }

    @Given("user should validate message in Alert Box")
    public void checkInValidLoginStatus() {
        logger.info("[LOGIN-STEP] Validating alert box...");
        loginPage = new LoginPage(getDriver());
        try {
            loginPage.validateAlertBox();
            ExtentReportManager.getTest().log(Status.PASS, "Alert box validated successfully");
            logger.info("[LOGIN-STEP] Alert box validated successfully ✅");
        } catch (Exception e) {
            logger.error("[LOGIN-STEP] Failed to validate alert box: " + e.getMessage(), e);
            String path = ScreenshotUtility.takeScreenshot(getDriver(), "AlertBoxFail");
            ExtentReportManager.getTest()
                    .log(Status.FAIL, "❌ Failed to validate alert box: " + e.getMessage())
                    .addScreenCaptureFromPath(path);
            throw e;
        }
    }

    @Given("user enters details using keyboard actions")
    public void enterLoginDetailsWithKeyboardAct() throws Exception {
        logger.info("[LOGIN-STEP] Entering login details using keyboard actions...");
        loginPage = new LoginPage(getDriver());
        try {
            ExtentReportManager.getTest().log(Status.INFO, "Entering login details using keyboard actions");

            String username = ConfigPropertiesUtility.getProperty("userName");
            String password = ConfigPropertiesUtility.getProperty("password");

            logger.info("[LOGIN-STEP] Username: " + username);
            logger.info("[LOGIN-STEP] Password: ********");

            loginPage.enterLoginDetailsWithKeyboardActions(username, password);

            ExtentReportManager.getTest().log(Status.PASS, "Entered login details successfully using keyboard actions");
            logger.info("[LOGIN-STEP] Keyboard action login successful ✅");
        } catch (Exception e) {
            logger.error("[LOGIN-STEP] Failed to enter login details via keyboard: " + e.getMessage(), e);
            String path = ScreenshotUtility.takeScreenshot(getDriver(), "KeyboardActionsFail");
            ExtentReportManager.getTest()
                    .log(Status.FAIL, "❌ Failed to enter login details using keyboard actions: " + e.getMessage())
                    .addScreenCaptureFromPath(path);
            throw e;
        }
    }
}