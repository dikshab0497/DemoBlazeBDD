package StepDefinitions;

import com.aventstack.extentreports.Status;

import io.cucumber.java.en.Given;
import pages.LoginPage;
import testBase.BaseClass;
import utilities.ExtentReportManager;
import utilities.TestContext;

public class LoginStepDefinition extends BaseClass {

    LoginPage loginPage;
           
    @Given("user enters username and password")
    public void enterLoginCredential() throws InterruptedException  {
       
        loginPage = new LoginPage(driver);
        
        
        String username = configProp.getProperty("userName");
       
        String password = configProp.getProperty("password");
        
        
        System.out.print(username);
        loginPage.enterLoginCredentials(username, password);
    }
    
    
    @Given("user should validate message in Alert Box")
    public void checkInValidLoginStatus() {
    	loginPage = new LoginPage(driver);
    	
    	loginPage.validateAlertBox();
    	
     }
    
    @Given("user enters details using keyboard actions")
    public void enterLoginDetailsWithKeyboardAct() throws InterruptedException {
    	loginPage = new LoginPage(driver);
    	 try {
        ExtentReportManager.getTest().log(Status.INFO, "Entering Login details");
        
    	String username = configProp.getProperty("userName");
        
        String password = configProp.getProperty("password");
       
    	loginPage.enterLoginDetailsWithKeyboardActions(username, password);
    	ExtentReportManager.getTest().log(Status.PASS, "Entered Login details");
    	
     }catch(Exception e) {
    	    ExtentReportManager.getTest().log(Status.FAIL, "❌ Failed to click Login button: " + e.getMessage());
    	    throw e;
     }
    }
}
