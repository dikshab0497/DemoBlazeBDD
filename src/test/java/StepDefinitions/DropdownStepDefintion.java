package StepDefinitions;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import testBase.BaseClass;
import utilities.ConfigPropertiesUtility;

public class DropdownStepDefintion extends BaseClass {

    private static final By DROPDOWN = By.id("dropdown");

    @Given("I navigate to the dropdown page")
    public void i_navigate_to_the_dropdown_page() throws IOException {
        String url = ConfigPropertiesUtility.getProperty("dropdown_url");
        logger.info("[DROPDOWN-STEP] Navigating to dropdown page: " + url);
        getDriver().get(url);
        logger.info("[DROPDOWN-STEP] Page loaded");
    }

    @When("I select {string} from the dropdown")
    public void i_select_from_the_dropdown(String value) {
        logger.info("[DROPDOWN-STEP] Selecting value from dropdown: " + value);
        WebElement dropdownElement = getDriver().findElement(DROPDOWN);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(value);
        logger.info("[DROPDOWN-STEP] Value selected: " + value);
    }

    @Then("the selected value should be {string}")
    public void the_selected_value_should_be(String expectedValue) {
        logger.info("[DROPDOWN-STEP] Validating selected dropdown value...");
        WebElement dropdownElement = getDriver().findElement(DROPDOWN);
        Select dropdown = new Select(dropdownElement);
        String actualValue = dropdown.getFirstSelectedOption().getText();
        logger.info("[DROPDOWN-STEP] Expected: " + expectedValue + " | Actual: " + actualValue);

        assertEquals(actualValue, expectedValue, "Selected value is not correct!");
        logger.info("[DROPDOWN-STEP] Assertion passed — dropdown value matches ✅");
    }
}