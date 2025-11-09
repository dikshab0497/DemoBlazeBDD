package StepDefinitions;
import io.cucumber.java.en.*;
import testBase.BaseClass;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import hooks.Hooks;

import static org.testng.Assert.*;

public class DropdownStepDefintion extends BaseClass {
	 WebDriver driver = Hooks.driver; // reuse your existing driver

	    @Given("I navigate to the dropdown page")
	    public void i_navigate_to_the_dropdown_page() {
	    	driver.get(configProp.getProperty("dropdown_url"));

	    }

	    @When("I select {string} from the dropdown")
	    public void i_select_from_the_dropdown(String value) {
	        WebElement dropdownElement = driver.findElement(By.id("dropdown"));
	        Select dropdown = new Select(dropdownElement);
	        dropdown.selectByVisibleText(value);
	    }

	    @Then("the selected value should be {string}")
	    public void the_selected_value_should_be(String expectedValue) {
	        WebElement dropdownElement = driver.findElement(By.id("dropdown"));
	        Select dropdown = new Select(dropdownElement);
	        String actualValue = dropdown.getFirstSelectedOption().getText();
	        assertEquals(actualValue, expectedValue, "Selected value is not correct!");
	    }
}
