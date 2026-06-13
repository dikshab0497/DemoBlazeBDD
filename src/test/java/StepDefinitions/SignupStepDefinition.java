package StepDefinitions;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import pages.SignupPage;
import testBase.BaseClass;
import utilities.ConfigPropertiesUtility;

public class SignupStepDefinition extends BaseClass {

    SignupPage signup;

    @Given("user enters username and password For SignUP")
    public void enterSignUpCredential() throws InterruptedException, IOException {
        logger.info("[SIGNUP-STEP] Entering Sign Up credentials...");
        signup = new SignupPage(getDriver());

        String username = ConfigPropertiesUtility.getProperty("qa.userName1");
        String password = ConfigPropertiesUtility.getProperty("qa.password1");

        logger.info("[SIGNUP-STEP] Username: " + username);
        logger.info("[SIGNUP-STEP] Password: ********");

        signup.enterSignUpCredentials(username, password);
        logger.info("[SIGNUP-STEP] Sign Up credentials entered");
    }

    @Given("user validate successful message in Alert Box")
    public void checkSuccessfulStatus() {
        logger.info("[SIGNUP-STEP] Validating success alert box...");
        signup = new SignupPage(getDriver());

        String actualMessage = signup.validateSuccessfulAlertBox();
        String expectedMessage = "Sign up successful.";

        logger.info("[SIGNUP-STEP] Expected: " + expectedMessage + " | Actual: " + actualMessage);
        Assert.assertEquals(actualMessage, expectedMessage, "Sign up Unsuccessful.");
        logger.info("[SIGNUP-STEP] Success alert assertion passed ✅");
    }

    @Given("user validate Warning message in Alert Box")
    public void checkWarningStatus() {
        logger.info("[SIGNUP-STEP] Validating user already exists warning...");
        signup = new SignupPage(getDriver());

        String actualMessage = signup.validateWarningAlertBox();
        String expectedMessage = "This user already exist.";

        logger.info("[SIGNUP-STEP] Expected: " + expectedMessage + " | Actual: " + actualMessage);
        Assert.assertEquals(actualMessage, expectedMessage, "Sign up Unsuccessful.");
        logger.info("[SIGNUP-STEP] User already exists warning assertion passed ✅");
    }

    @Given("user validate Warning message While signUp in Alert Box")
    public void checkWarningSignUpStatus() {
        logger.info("[SIGNUP-STEP] Validating missing fields warning...");
        signup = new SignupPage(getDriver());

        String actualMessage = signup.validateWarningAlertBox();
        String expectedMessage = "Please fill out Username and Password.";

        logger.info("[SIGNUP-STEP] Expected: " + expectedMessage + " | Actual: " + actualMessage);
        Assert.assertEquals(actualMessage, expectedMessage, "Sign up Unsuccessful.");
        logger.info("[SIGNUP-STEP] Missing fields warning assertion passed ✅");
    }
}