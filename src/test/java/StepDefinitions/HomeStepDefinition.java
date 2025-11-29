package StepDefinitions;

import java.util.List;

import org.testng.Assert;

import com.aventstack.extentreports.Status;

import io.cucumber.java.en.Given;
import pages.HomePage;
import testBase.BaseClass;
import utilities.ExtentReportManager;

public class HomeStepDefinition extends BaseClass {

	HomePage homePage;

	@Given("user clicks on SignUP button")
    public void clickOnSignUpButton() throws InterruptedException {
       
        homePage = new HomePage(driver);

        homePage.clickSignUp();
    }
	

    @Given("user clicks on LogIn button")
    public void clickOnLoginButton() throws InterruptedException {
    	homePage = new HomePage(driver);
       try {
           ExtentReportManager.getTest().log(Status.INFO, "Clicking on Login button");
           homePage.clickLogIn();
           ExtentReportManager.getTest().log(Status.PASS, "Clicked on Login button successfully");
       }catch(Exception e) {
    	   ExtentReportManager.getTest().log(Status.FAIL, "Failed to click on Login button: " + e.getMessage());
           throw e; 
       }
        
    }
    
    @Given("user should be logged in and redirected to homepage")
    public void checkLoginSussefulorNot() {
       
    	homePage = new HomePage(driver);
    	try {
            ExtentReportManager.getTest().log(Status.INFO, "Redirecting homepage");
            
        String env = configProp.getProperty("env","qa");
        String username = configProp.getProperty(env + ".userName");
          
        Assert.assertEquals(homePage.getWelcomeText(), "Welcome "+ username, "Welcome text does not match!");
        ExtentReportManager.getTest().log(Status.PASS, "Redirection succesfull");
    	}catch(Exception e) {
    		
    	    ExtentReportManager.getTest().log(Status.FAIL, "❌ Failed to Redirect: " + e.getMessage());
    	    throw e;
     }
    	}
     
   
    @Given("user clicks on LogOut button")
    public void clickLogOutButton() throws InterruptedException {
       
        homePage = new HomePage(driver);

        homePage.clickLogOut();
    }

    @Given("User clicks on the category of product")
    public void selectCategory() throws InterruptedException {
       
        homePage = new HomePage(driver);

        homePage.clickCategory();
    }

    @Given("All displayed products should belong to the selected category")
    public void validateProductFromCategory() throws InterruptedException {
       
        homePage = new HomePage(driver);

        String productName = homePage.getProductDetails();
        String keyword = "MacBook air";
        
        Assert.assertTrue(
        	    productName.contains(keyword),
        	    "Product '" + productName + "' does not belong to the selected category"
        	);
    }

    @Given("User click on the product")
    public void clickProductFromCategory() throws InterruptedException {
       
        homePage = new HomePage(driver);

        homePage.clickLaptopProduct();
    }

    @Given("User Scrolls the page")
    public void scrollPageDown() throws InterruptedException {
       
        homePage = new HomePage(driver);

        homePage.scrollToBottom();
    }
    @Given("User fetches all products across pages")
    public void fetchAllProducts() throws InterruptedException {
    	homePage = new HomePage(driver);

        List<String> allProducts = homePage.getAllProductNames();

        System.out.println("Total products found: " + allProducts.size());
        for (String productName : allProducts) {
            System.out.println(productName);
        }
    }


}
