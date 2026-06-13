package pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LaptopProductsPage extends BasePage {

    private static final By HEADER_MACBOOK   = By.xpath("//h2[normalize-space()='MacBook air']");
    private static final By BTN_ADD_TO_CART  = By.xpath("//a[normalize-space()='Add to cart']");
    private static final By TXT_PRICE        = By.xpath("//h3[@class='price-container']");
    private static final By LNK_MACBOOK_PRO  = By.xpath("//a[normalize-space()='MacBook Pro']");

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @FindBy(xpath = "//h2[normalize-space()='MacBook air']")
    WebElement headerLaptopProduct;

    @FindBy(xpath = "//a[normalize-space()='Add to cart']")
    WebElement btnAddToCart;

    @FindBy(xpath = "//a[@id='cartur']")
    WebElement linkCart;

    @FindBy(xpath = "//button[normalize-space()='Place Order']")
    WebElement btnPlaceOrder;

    @FindBy(xpath = "//li[@class='nav-item active']//a[@class='nav-link']")
    WebElement linkHome;

    @FindBy(xpath = "//a[normalize-space()='MacBook Pro']")
    WebElement scrollProd;

    public LaptopProductsPage(WebDriver driver) {
        super(driver);
    }

    public String getProductDetailsFromSelectedCategory() throws InterruptedException {
        logger.info("[LAPTOP] Waiting for product header...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(HEADER_MACBOOK));
        String header = headerLaptopProduct.getText();
        logger.info("[LAPTOP] Product header: " + header);
        return header;
    }

    public void clickOnAddToCart() {
        logger.info("[LAPTOP] Clicking Add to Cart...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(BTN_ADD_TO_CART));
        btnAddToCart.click();
        logger.info("[LAPTOP] Add to Cart clicked");
    }

    public String getProductName() {
        logger.info("[LAPTOP] Fetching product name...");
        String name = driver.findElement(By.cssSelector(".name")).getText();
        logger.info("[LAPTOP] Product name: " + name);
        return name;
    }

    public String getProductPrice() {
        logger.info("[LAPTOP] Fetching product price...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(TXT_PRICE));
        String price = driver.findElement(By.cssSelector(".price-container")).getText();
        logger.info("[LAPTOP] Product price: " + price);
        return price;
    }

    public String getProductDescription() {
        logger.info("[LAPTOP] Fetching product description...");
        String description = driver.findElement(By.cssSelector("#more-information")).getText();
        logger.info("[LAPTOP] Product description: " + description);
        return description;
    }

    public void clickOnAddToCartAlertBox() {
        logger.info("[LAPTOP] Waiting for Add to Cart alert...");
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        logger.info("[LAPTOP] Alert message: " + alert.getText()); // ✅ replaced System.out.println
        alert.accept();
        logger.info("[LAPTOP] Alert accepted");
    }

    public void clickOnCart() {
        logger.info("[LAPTOP] Navigating to Cart...");
        linkCart.click();
    }

    public void clickOnHome() {
        logger.info("[LAPTOP] Navigating to Home...");
        linkHome.click();
    }

    public void clickOnPlaceOrderbtn() {
        logger.info("[LAPTOP] Clicking Place Order button...");
        btnPlaceOrder.click();
    }

    public void scrollPageTillProduct() throws InterruptedException {
        logger.info("[LAPTOP] Scrolling to MacBook Pro...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(LNK_MACBOOK_PRO));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scrollProd);
        Thread.sleep(2000);
        scrollProd.click();
        logger.info("[LAPTOP] MacBook Pro clicked after scroll");
    }
}