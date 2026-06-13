package StepDefinitions;

import java.util.List;

import org.testng.Assert;

import com.aventstack.extentreports.Status;

import io.cucumber.java.en.Given;
import pages.HomePage;
import testBase.BaseClass;
import utilities.ConfigPropertiesUtility;
import utilities.ExtentReportManager;

public class HomeStepDefinition extends BaseClass {

    HomePage homePage;

    @Given("user clicks on SignUP button")
    public void clickOnSignUpButton() throws InterruptedException {
        logger.info("[HOME-STEP] Clicking Sign Up button...");
        homePage = new HomePage(getDriver());
        homePage.clickSignUp();
        logger.info("[HOME-STEP] Sign Up button clicked");
    }

    @Given("user clicks on LogIn button")
    public void clickOnLoginButton() throws InterruptedException {
        logger.info("[HOME-STEP] Clicking Login button...");
        homePage = new HomePage(getDriver());
        try {
            ExtentReportManager.getTest().log(Status.INFO, "Clicking on Login button");
            homePage.clickLogIn();
            ExtentReportManager.getTest().log(Status.PASS, "Clicked on Login button successfully");
            logger.info("[HOME-STEP] Login button clicked successfully");
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "Failed to click on Login button: " + e.getMessage());
            logger.error("[HOME-STEP] Failed to click Login button: " + e.getMessage(), e);
            throw e;
        }
    }

    @Given("user should be logged in and redirected to homepage")
    public void checkLoginSussefulorNot() throws Exception {
        logger.info("[HOME-STEP] Validating login and homepage redirection...");
        homePage = new HomePage(getDriver());
        try {
            ExtentReportManager.getTest().log(Status.INFO, "Redirecting to homepage");
            String username = ConfigPropertiesUtility.getProperty("qa.userName");
            String expectedText = "Welcome " + username;
            String actualText = homePage.getWelcomeText();

            logger.info("[HOME-STEP] Expected welcome text: " + expectedText);
            logger.info("[HOME-STEP] Actual welcome text  : " + actualText);

            Assert.assertEquals(actualText, expectedText, "Welcome text does not match!");

            ExtentReportManager.getTest().log(Status.PASS, "Redirection successful");
            logger.info("[HOME-STEP] Login validation passed ✅");
        } catch (Exception e) {
            ExtentReportManager.getTest().log(Status.FAIL, "❌ Failed to redirect: " + e.getMessage());
            logger.error("[HOME-STEP] Login validation failed: " + e.getMessage(), e);
            throw e;
        }
    }

    @Given("user clicks on LogOut button")
    public void clickLogOutButton() throws InterruptedException {
        logger.info("[HOME-STEP] Clicking Logout button...");
        homePage = new HomePage(getDriver());
        homePage.clickLogOut();
        logger.info("[HOME-STEP] Logout button clicked");
    }

    @Given("User clicks on the category of product")
    public void selectCategory() throws InterruptedException {
        logger.info("[HOME-STEP] Selecting product category...");
        homePage = new HomePage(getDriver());
        homePage.clickCategory();
        logger.info("[HOME-STEP] Category selected");
    }

    @Given("All displayed products should belong to the selected category")
    public void validateProductFromCategory() throws InterruptedException {
        logger.info("[HOME-STEP] Validating product belongs to selected category...");
        homePage = new HomePage(getDriver());
        String productName = homePage.getProductDetails();
        String keyword = "MacBook air";

        logger.info("[HOME-STEP] Product found: " + productName);
        logger.info("[HOME-STEP] Asserting product contains keyword: " + keyword);

        Assert.assertTrue(
                productName.contains(keyword),
                "Product '" + productName + "' does not belong to the selected category"
        );
        logger.info("[HOME-STEP] Category assertion passed ✅");
    }

    @Given("User click on the product")
    public void clickProductFromCategory() throws InterruptedException {
        logger.info("[HOME-STEP] Clicking on product...");
        homePage = new HomePage(getDriver());
        homePage.clickLaptopProduct();
        logger.info("[HOME-STEP] Product clicked");
    }

    @Given("User Scrolls the page")
    public void scrollPageDown() throws InterruptedException {
        logger.info("[HOME-STEP] Scrolling page to bottom...");
        homePage = new HomePage(getDriver());
        homePage.scrollToBottom();
        logger.info("[HOME-STEP] Scroll complete");
    }

    @Given("User fetches all products across pages")
    public void fetchAllProducts() throws InterruptedException {
        logger.info("[HOME-STEP] Fetching all products across pages...");
        homePage = new HomePage(getDriver());
        List<String> allProducts = homePage.getAllProductNames();

        logger.info("[HOME-STEP] Total products found: " + allProducts.size());
        for (String productName : allProducts) {
            logger.info("[HOME-STEP] Product: " + productName);
        }
    }
}