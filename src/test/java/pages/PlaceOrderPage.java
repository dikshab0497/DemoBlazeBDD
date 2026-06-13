package pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PlaceOrderPage extends BasePage {

    private static final By FIELD_NAME = By.id("name");

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @FindBy(xpath = "//input[@id='name']")
    WebElement fieldName;

    @FindBy(xpath = "//input[@id='country']")
    WebElement fieldCountry;

    @FindBy(xpath = "//input[@id='city']")
    WebElement fieldCity;

    @FindBy(xpath = "//input[@id='card']")
    WebElement fieldCreditCard;

    @FindBy(xpath = "//input[@id='month']")
    WebElement fieldMonth;

    @FindBy(xpath = "//input[@id='year']")
    WebElement fieldYear;

    @FindBy(xpath = "//button[normalize-space()='Purchase']")
    WebElement btnPurchase;

    public PlaceOrderPage(WebDriver driver) {
        super(driver);
    }

    public void enterPlaceOrderDetails() throws InterruptedException {
        logger.info("[ORDER] Waiting for Place Order form...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIELD_NAME));

        logger.info("[ORDER] Filling in order details...");
        fieldName.sendKeys("");
        fieldCountry.sendKeys("");
        fieldCity.sendKeys("");
        fieldCreditCard.sendKeys(" ");
        fieldMonth.sendKeys("11");
        fieldYear.sendKeys("27");

        logger.info("[ORDER] Clicking Purchase button...");
        btnPurchase.click();
        logger.info("[ORDER] Purchase button clicked");
    }

    public void clickOnAddToCartAlertBox() {
        logger.info("[ORDER] Waiting for alert...");
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        logger.info("[ORDER] Alert message: " + alert.getText()); // ✅ replaced System.out.println
        alert.accept();
        logger.info("[ORDER] Alert accepted");
    }
}