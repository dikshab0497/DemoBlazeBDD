package StepDefinitions;

import testBase.BaseClass;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import hooks.Hooks;

import static org.testng.Assert.*;

public class DraganddropStepDefinition extends BaseClass {
	WebDriver driver = Hooks.driver;

    @Given("I navigate to the drag and drop page")
    public void i_navigate_to_the_drag_and_drop_page() {
        driver.get(configProp.getProperty("dragdrop_url"));
    }

    @When("I drag the element A and drop it onto element B")
    public void i_drag_the_element_a_and_drop_it_onto_element_b() {
        WebElement source = driver.findElement(By.id("column-a"));
        WebElement target = driver.findElement(By.id("column-b"));
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target).perform();
    }

    @Then("the elements should be swapped successfully")
    public void the_elements_should_be_swapped_successfully() {
        WebElement headerA = driver.findElement(By.xpath("//div[@id='column-a']/header"));
        String textA = headerA.getText();
        // After drag and drop, column A should now contain "B"
        assertEquals(textA, "B", "Drag and Drop failed — element not swapped!");
    }

}
