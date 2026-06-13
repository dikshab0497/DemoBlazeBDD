package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage {

    private static final By PRODUCT_MACBOOK    = By.xpath("//td[normalize-space()='MacBook air']");
    private static final By DELETE_LINK        = By.xpath("//a[normalize-space()='Delete']");
    private static final By TOTAL_AMOUNT       = By.xpath("//h3[@id='totalp']");

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @FindBy(xpath = "//td[normalize-space()='MacBook air']")
    WebElement productDetails;

    @FindBy(xpath = "//a[normalize-space()='Delete']")
    WebElement LnkprodDelete;

    @FindBy(xpath = "//h3[@id='totalp']")
    WebElement totalAmount;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String getProductFromAddToCart() throws InterruptedException {
        logger.info("[CART] Waiting for product to appear in cart...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_MACBOOK));
        String product = productDetails.getText();
        logger.info("[CART] Product found in cart: " + product);
        return product;
    }

    public void deleteProductFromCart() {
        logger.info("[CART] Waiting for delete link...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(DELETE_LINK));
        LnkprodDelete.click();
        logger.info("[CART] Delete clicked for product");
    }

    public List<WebElement> validateProductDeletion(String deletedProduct) {
        logger.info("[CART] Validating deletion of product: " + deletedProduct);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(PRODUCT_MACBOOK));
        List<WebElement> remainingItems = driver.findElements(
                By.xpath("//td[normalize-space()='" + deletedProduct + "']"));
        logger.info("[CART] Remaining items with name '" + deletedProduct + "': " + remainingItems.size());
        return remainingItems;
    }

    public String getTotalAmount() throws InterruptedException {
        logger.info("[CART] Fetching total amount...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(TOTAL_AMOUNT));
        String amount = totalAmount.getText();
        logger.info("[CART] Total amount: " + amount);
        return amount;
    }
}