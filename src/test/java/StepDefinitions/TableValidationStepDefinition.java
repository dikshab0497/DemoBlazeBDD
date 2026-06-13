package StepDefinitions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import testBase.BaseClass;

public class TableValidationStepDefinition extends BaseClass {

    private static final String TABLE_URL      = "https://the-internet.herokuapp.com/tables";
    private static final By LAST_NAME_CELLS    = By.xpath("//table[@id='table1']//tr/td[1]");

    List<String> originalList = new ArrayList<>();
    List<String> sortedList   = new ArrayList<>();

    @Given("I navigate to the table page")
    public void i_navigate_to_the_table_page() {
        logger.info("[TABLE-STEP] Navigating to table page: " + TABLE_URL);
        getDriver().get(TABLE_URL);
        logger.info("[TABLE-STEP] Table page loaded");
    }

    @When("I click on the {string} column header")
    public void i_click_on_the_column_header(String columnName) {
        logger.info("[TABLE-STEP] Clicking on column header: " + columnName);
        getDriver().findElement(By.xpath("//span[text()='" + columnName + "']")).click();
        logger.info("[TABLE-STEP] Column header clicked: " + columnName);
    }

    @Then("the table should be sorted in ascending order by {string}")
    public void the_table_should_be_sorted_in_ascending_order_by(String columnName) {
        logger.info("[TABLE-STEP] Validating table sorted in ascending order by: " + columnName);

        List<WebElement> lastNameElements = getDriver().findElements(LAST_NAME_CELLS);
        for (WebElement e : lastNameElements) {
            originalList.add(e.getText().trim());
        }

        sortedList.addAll(originalList);
        Collections.sort(sortedList);

        logger.info("[TABLE-STEP] Original List : " + originalList);
        logger.info("[TABLE-STEP] Expected Sorted: " + sortedList);

        Assert.assertEquals(originalList, sortedList, "Table not sorted in ascending order!");
        logger.info("[TABLE-STEP] Sort assertion passed ✅");
    }
}