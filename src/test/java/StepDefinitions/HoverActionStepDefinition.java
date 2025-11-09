package StepDefinitions;

import testBase.BaseClass;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import hooks.Hooks;

import static org.testng.Assert.*;

public class HoverActionStepDefinition extends BaseClass {
	
	 WebDriver driver = Hooks.driver;

	    @Given("I navigate to the hover page")
	    public void i_navigate_to_the_hover_page() {
	        driver.get(configProp.getProperty("hover_url"));
	    }

	    @When("I hover over the first image")
	    public void i_hover_over_the_first_image() throws InterruptedException {
	        WebElement firstImage = driver.findElement(By.xpath("(//div[@class='figure'])[1]"));
	        Actions actions = new Actions(driver);
	        actions.moveToElement(firstImage).perform();
	        Thread.sleep(100);
	    }

	    @Then("I should see the user information displayed")
	    public void i_should_see_the_user_information_displayed() {
	        WebElement caption = driver.findElement(By.xpath("(//div[@class='figcaption'])[1]"));
	        assertTrue(caption.isDisplayed(), "User information not displayed after hover!");
	    }

}
