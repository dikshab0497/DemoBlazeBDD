package StepDefinitions;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import pages.SignupPage;
import testBase.BaseClass;

public class SignupStepDefinition extends BaseClass{
	
	SignupPage signup;
    
	@Given("user enters username and password For SignUP")
    public void enterSignUpCredential() throws InterruptedException  {
       
		signup = new SignupPage(driver);
        
		String env = configProp.getProperty("env","qa").toLowerCase();
        
        String username = configProp.getProperty(env + ".userName1");
       
        String password = configProp.getProperty(env + ".password1");
        
        
        System.out.print(username);
        signup.enterSignUpCredentials(username, password);
    }
	
	@Given("user validate successful message in Alert Box")
    public void checkSuccessfulStatus() {
		signup = new SignupPage(driver);
    	
		Assert.assertEquals(signup.validateSuccessfulAlertBox(), "Sign up successful.", "Sign up Unsuccessful.");
    	
     }
	
	@Given("user validate Warning message in Alert Box")
    public void checkWarningStatus() {
		signup = new SignupPage(driver);
    	
		Assert.assertEquals(signup.validateWarningAlertBox(), "This user already exist.", "Sign up Unsuccessful.");
    	
     }
    
	@Given("user validate Warning message While signUp in Alert Box")
    public void checkWarningSignUpStatus() {
		signup = new SignupPage(driver);
    	
		Assert.assertEquals(signup.validateWarningAlertBox(), "Please fill out Username and Password.", "Sign up Unsuccessful.");
    	
     }
    
    
}
