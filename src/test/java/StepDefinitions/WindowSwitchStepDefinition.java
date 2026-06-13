package StepDefinitions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.By;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import testBase.BaseClass;
import utilities.ConfigPropertiesUtility;

public class WindowSwitchStepDefinition extends BaseClass {

    String parentWindow;

    @Given("I navigate to the window page")
    public void i_navigate_to_the_window_page() throws IOException {
        String url = ConfigPropertiesUtility.getProperty("window_url");
        logger.info("[WINDOW-STEP] Navigating to window page: " + url);
        getDriver().get(url);
        parentWindow = getDriver().getWindowHandle();
        logger.info("[WINDOW-STEP] Parent window handle stored: " + parentWindow);
    }

    @When("I click on the {string} link")
    public void i_click_on_the_link(String linkText) {
        logger.info("[WINDOW-STEP] Clicking link: " + linkText);
        getDriver().findElement(By.linkText(linkText)).click();
        logger.info("[WINDOW-STEP] Link clicked: " + linkText);
    }

    @Then("a new window should open")
    public void a_new_window_should_open() {
        int windowCount = getDriver().getWindowHandles().size();
        logger.info("[WINDOW-STEP] Total windows open: " + windowCount);
        assertTrue(windowCount > 1, "New window not opened!");
        logger.info("[WINDOW-STEP] New window opened ✅");
    }

    @Then("I switch to the new window")
    public void i_switch_to_the_new_window() {
        logger.info("[WINDOW-STEP] Switching to new window...");
        Set<String> windows = getDriver().getWindowHandles();
        for (String win : windows) {
            if (!win.equals(parentWindow)) {
                getDriver().switchTo().window(win);
                logger.info("[WINDOW-STEP] Switched to window handle: " + win);
                break;
            }
        }
    }

    @Then("I verify the page title is {string}")
    public void i_verify_the_page_title_is(String expectedTitle) {
        String actualTitle = getDriver().getTitle();
        logger.info("[WINDOW-STEP] Expected title: " + expectedTitle + " | Actual: " + actualTitle);
        assertEquals(actualTitle, expectedTitle, "Title mismatch in new window!");
        logger.info("[WINDOW-STEP] Page title assertion passed ✅");
    }

    @Then("I close the new window and return to the parent window")
    public void i_close_the_new_window_and_return_to_the_parent_window() {
        logger.info("[WINDOW-STEP] Closing new window...");
        getDriver().close();
        getDriver().switchTo().window(parentWindow);
        logger.info("[WINDOW-STEP] Switched back to parent window: " + parentWindow);
    }
}