package StepDefinitions;

import testBase.BaseClass;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;

import hooks.Hooks;

import static org.testng.Assert.*;
import java.util.Set;

public class WindowSwitchStepDefinition extends BaseClass{
	
	 WebDriver driver = Hooks.driver;
	    String parentWindow;

	    @Given("I navigate to the window page")
	    public void i_navigate_to_the_window_page() {
	        driver.get(configProp.getProperty("window_url"));
	        parentWindow = driver.getWindowHandle();
	    }

	    @When("I click on the {string} link")
	    public void i_click_on_the_link(String linkText) {
	        driver.findElement(By.linkText(linkText)).click();
	    }

	    @Then("a new window should open")
	    public void a_new_window_should_open() {
	        assertTrue(driver.getWindowHandles().size() > 1, "New window not opened!");
	    }

	    @Then("I switch to the new window")
	    public void i_switch_to_the_new_window() {
	        Set<String> windows = driver.getWindowHandles();
	        for (String win : windows) {
	            if (!win.equals(parentWindow)) {
	                driver.switchTo().window(win);
	                break;
	            }
	        }
	    }

	    @Then("I verify the page title is {string}")
	    public void i_verify_the_page_title_is(String expectedTitle) {
	        assertEquals(driver.getTitle(), expectedTitle, "Title mismatch in new window!");
	    }

	    @Then("I close the new window and return to the parent window")
	    public void i_close_the_new_window_and_return_to_the_parent_window() {
	        driver.close();
	        driver.switchTo().window(parentWindow);
	    }

}
