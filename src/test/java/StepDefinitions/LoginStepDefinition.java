package StepDefinitions;

import com.aventstack.extentreports.Status;
import io.cucumber.java.en.Given;
import pages.LoginPage;
import testBase.BaseClass;
import utilities.ExtentReportManager;
import utilities.ScreenshotUtility;

public class LoginStepDefinition extends BaseClass {

    LoginPage loginPage;

    @Given("user enters username and password")
    public void enterLoginCredential() throws InterruptedException {
        loginPage = new LoginPage(driver);
        try {
        	
        	String env = configProp.getProperty("env","qa").toLowerCase();
            String username = configProp.getProperty(env + ".userName");
            String password = configProp.getProperty(env + ".password");

            loginPage.enterLoginCredentials(username,password);

            ExtentReportManager.getTest().log(Status.PASS, "Entered username and password");
        } catch (Exception e) {
            String path = ScreenshotUtility.takeScreenshot(driver, "LoginCredentialsFail");
            ExtentReportManager.getTest().log(Status.FAIL, "❌ Failed to enter login credentials: " + e.getMessage())
                    .addScreenCaptureFromPath(path);
            throw e;
        }
    }

    @Given("user should validate message in Alert Box")
    public void checkInValidLoginStatus() {
        loginPage = new LoginPage(driver);
        try {
            loginPage.validateAlertBox();
            ExtentReportManager.getTest().log(Status.PASS, "Alert box validated successfully");
        } catch (Exception e) {
            String path = ScreenshotUtility.takeScreenshot(driver, "AlertBoxFail");
            ExtentReportManager.getTest().log(Status.FAIL, "❌ Failed to validate alert box: " + e.getMessage())
                    .addScreenCaptureFromPath(path);
            throw e;
        }
    }

    @Given("user enters details using keyboard actions")
    public void enterLoginDetailsWithKeyboardAct() throws InterruptedException {
        loginPage = new LoginPage(driver);
        try {
            ExtentReportManager.getTest().log(Status.INFO, "Entering login details using keyboard actions");

            String username = configProp.getProperty("userName");
            String password = configProp.getProperty("password");

            loginPage.enterLoginDetailsWithKeyboardActions(username, password);

            ExtentReportManager.getTest().log(Status.PASS, "Entered login details successfully using keyboard actions");
        } catch (Exception e) {
            String path = ScreenshotUtility.takeScreenshot(driver, "KeyboardActionsFail");
            ExtentReportManager.getTest().log(Status.FAIL, "❌ Failed to enter login details using keyboard actions: " + e.getMessage())
                    .addScreenCaptureFromPath(path);
            throw e;
        }
    }
}
