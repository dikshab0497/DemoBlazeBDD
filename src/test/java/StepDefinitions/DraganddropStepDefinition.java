package StepDefinitions;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import testBase.BaseClass;
import utilities.ConfigPropertiesUtility;

public class DraganddropStepDefinition extends BaseClass {

    private static final By SOURCE_ELEMENT   = By.id("column-a");
    private static final By TARGET_ELEMENT   = By.id("column-b");
    private static final By HEADER_COLUMN_A  = By.xpath("//div[@id='column-a']/header");

    @Given("I navigate to the drag and drop page")
    public void i_navigate_to_the_drag_and_drop_page() throws IOException {
        String url = ConfigPropertiesUtility.getProperty("dragdrop_url");
        logger.info("[DRAGDROP-STEP] Navigating to drag and drop page: " + url);
        getDriver().get(url);
        logger.info("[DRAGDROP-STEP] Page loaded");
    }

    @When("I drag the element A and drop it onto element B")
    public void i_drag_the_element_a_and_drop_it_onto_element_b() {
        logger.info("[DRAGDROP-STEP] Locating source and target elements...");
        WebElement source = getDriver().findElement(SOURCE_ELEMENT);
        WebElement target = getDriver().findElement(TARGET_ELEMENT);

        logger.info("[DRAGDROP-STEP] Performing drag and drop...");
        Actions actions = new Actions(getDriver());
        actions.dragAndDrop(source, target).perform();
        logger.info("[DRAGDROP-STEP] Drag and drop performed");
    }

    @Then("the elements should be swapped successfully")
    public void the_elements_should_be_swapped_successfully() {
        logger.info("[DRAGDROP-STEP] Validating element swap...");
        WebElement headerA = getDriver().findElement(HEADER_COLUMN_A);
        String textA = headerA.getText();
        logger.info("[DRAGDROP-STEP] Column A header text after swap: " + textA);

        assertEquals(textA, "B", "Drag and Drop failed — element not swapped!");
        logger.info("[DRAGDROP-STEP] Assertion passed — elements swapped successfully ✅");
    }
}