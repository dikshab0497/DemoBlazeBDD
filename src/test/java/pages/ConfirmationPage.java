package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfirmationPage extends BasePage {

    private static final By ORDER_DETAILS_LOCATOR = By.xpath("//p[contains(@class,'lead text-muted')]");

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @FindBy(xpath = "//p[contains(@class,'lead text-muted')]")
    WebElement txtPurchaseOrderDetails;

    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public String getPurchaseOrderDetails() throws InterruptedException {
        logger.info("[CONFIRMATION] Waiting for purchase order details...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(ORDER_DETAILS_LOCATOR));
        String orderDetails = txtPurchaseOrderDetails.getText();
        logger.info("[CONFIRMATION] Order details: " + orderDetails);
        return orderDetails;
    }
}