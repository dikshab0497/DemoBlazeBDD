package StepDefinitions;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import testBase.BaseClass;
import utilities.ConfigPropertiesUtility;

public class HoverActionStepDefinition extends BaseClass {

    private static final By FIRST_IMAGE   = By.xpath("(//div[@class='figure'])[1]");
    private static final By FIRST_CAPTION = By.xpath("(//div[@class='figcaption'])[1]");

    @Given("I navigate to the hover page")
    public void i_navigate_to_the_hover_page() throws IOException {
        String url = ConfigPropertiesUtility.getProperty("hover_url");
        logger.info("[HOVER-STEP] Navigating to hover page: " + url);
        getDriver().get(url);
        logger.info("[HOVER-STEP] Page loaded");
    }

    @When("I hover over the first image")
    public void i_hover_over_the_first_image() throws InterruptedException {
        logger.info("[HOVER-STEP] Locating first image...");
        WebElement firstImage = getDriver().findElement(FIRST_IMAGE);
        Actions actions = new Actions(getDriver());
        actions.moveToElement(firstImage).perform();
        logger.info("[HOVER-STEP] Hovered over first image");
        Thread.sleep(100);
    }

    @Then("I should see the user information displayed")
    public void i_should_see_the_user_information_displayed() {
        logger.info("[HOVER-STEP] Validating user information is displayed after hover...");
        WebElement caption = getDriver().findElement(FIRST_CAPTION);
        boolean isDisplayed = caption.isDisplayed();
        logger.info("[HOVER-STEP] Caption displayed: " + isDisplayed);

        assertTrue(isDisplayed, "User information not displayed after hover!");
        logger.info("[HOVER-STEP] Assertion passed — user info visible after hover ✅");
    }
}