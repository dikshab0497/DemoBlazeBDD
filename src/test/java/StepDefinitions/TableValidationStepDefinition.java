package StepDefinitions;

import testBase.BaseClass;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import hooks.Hooks;

import java.util.*;

public class TableValidationStepDefinition extends BaseClass {
	
	WebDriver driver = Hooks.driver;
    List<String> originalList = new ArrayList<>();
    List<String> sortedList = new ArrayList<>();

    @Given("I navigate to the table page")
    public void i_navigate_to_the_table_page() {
        driver.get("https://the-internet.herokuapp.com/tables");
    }

    @When("I click on the {string} column header")
    public void i_click_on_the_column_header(String columnName) {
        driver.findElement(By.xpath("//span[text()='" + columnName + "']")).click();
    }

    @Then("the table should be sorted in ascending order by {string}")
    public void the_table_should_be_sorted_in_ascending_order_by(String columnName) {
        // Fetch all the data from the Last Name column
        List<WebElement> lastNameElements = driver.findElements(By.xpath("//table[@id='table1']//tr/td[1]"));

        for (WebElement e : lastNameElements) {
            originalList.add(e.getText().trim());
        }

        // Copy to another list for sorting
        sortedList.addAll(originalList);

        // Sort the copied list alphabetically
        Collections.sort(sortedList);

        System.out.println("Original List: " + originalList);
        System.out.println("Expected Sorted List: " + sortedList);

        // Verify both lists match
        Assert.assertEquals(originalList, sortedList, "Table not sorted in ascending order!");
    }

}
