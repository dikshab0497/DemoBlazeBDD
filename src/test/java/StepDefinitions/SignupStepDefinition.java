package StepDefinitions;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import pages.SignupPage;
import testBase.BaseClass;
import utilities.ConfigPropertiesUtility;

public class SignupStepDefinition extends BaseClass{
	
	SignupPage signup;
    
	@Given("user enters username and password For SignUP")
    public void enterSignUpCredential() throws InterruptedException, IOException  {
       
		signup = new SignupPage(getDriver());
        
//		String env = ConfigPropertiesUtility.getProperty("env").toLowerCase();
        
        String username = ConfigPropertiesUtility.getProperty("qa.userName1");
       
        String password = ConfigPropertiesUtility.getProperty("qa.password1");
        
        
        System.out.print(username);
        signup.enterSignUpCredentials(username, password);
    }
	
	@Given("user validate successful message in Alert Box")
    public void checkSuccessfulStatus() {
		signup = new SignupPage(getDriver());
    	
		Assert.assertEquals(signup.validateSuccessfulAlertBox(), "Sign up successful.", "Sign up Unsuccessful.");
    	
     }
	
	@Given("user validate Warning message in Alert Box")
    public void checkWarningStatus() {
		signup = new SignupPage(getDriver());
    	
		Assert.assertEquals(signup.validateWarningAlertBox(), "This user already exist.", "Sign up Unsuccessful.");
    	
     }
    
	@Given("user validate Warning message While signUp in Alert Box")
    public void checkWarningSignUpStatus() {
		signup = new SignupPage(getDriver());
    	
		Assert.assertEquals(signup.validateWarningAlertBox(), "Please fill out Username and Password.", "Sign up Unsuccessful.");
    	
     }
    
    
}
