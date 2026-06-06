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
        loginPage = new LoginPage(getDriver());
        try {
        	
//        	String env = ConfigPropertiesUtility.getProperty("env").toLowerCase();
            String username = ConfigPropertiesUtility.getProperty("qa.userName");
            String password = ConfigPropertiesUtility.getProperty("qa.password");

            loginPage.enterLoginCredentials(username,password);

            ExtentReportManager.getTest().log(Status.PASS, "Entered username and password");
        } catch (Exception e) {
            String path = ScreenshotUtility.takeScreenshot(getDriver(), "LoginCredentialsFail");
            ExtentReportManager.getTest().log(Status.FAIL, "❌ Failed to enter login credentials: " + e.getMessage())
                    .addScreenCaptureFromPath(path);
            throw e;
        }
    }

    @Given("user should validate message in Alert Box")
    public void checkInValidLoginStatus() {
        loginPage = new LoginPage(getDriver());
        try {
            loginPage.validateAlertBox();
            ExtentReportManager.getTest().log(Status.PASS, "Alert box validated successfully");
        } catch (Exception e) {
            String path = ScreenshotUtility.takeScreenshot(getDriver(), "AlertBoxFail");
            ExtentReportManager.getTest().log(Status.FAIL, "❌ Failed to validate alert box: " + e.getMessage())
                    .addScreenCaptureFromPath(path);
            throw e;
        }
    }

    @Given("user enters details using keyboard actions")
    public void enterLoginDetailsWithKeyboardAct() throws Exception {
        loginPage = new LoginPage(getDriver());
        try {
            ExtentReportManager.getTest().log(Status.INFO, "Entering login details using keyboard actions");

            String username = ConfigPropertiesUtility.getProperty("userName");
            String password = ConfigPropertiesUtility.getProperty("password");

            loginPage.enterLoginDetailsWithKeyboardActions(username, password);

            ExtentReportManager.getTest().log(Status.PASS, "Entered login details successfully using keyboard actions");
        } catch (Exception e) {
            String path = ScreenshotUtility.takeScreenshot(getDriver(), "KeyboardActionsFail");
            ExtentReportManager.getTest().log(Status.FAIL, "❌ Failed to enter login details using keyboard actions: " + e.getMessage())
                    .addScreenCaptureFromPath(path);
            throw e;
        }
    }
}
