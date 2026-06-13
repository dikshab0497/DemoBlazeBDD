package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    private static final By LOGIN_BTN        = By.id("login2");
    private static final By PRODUCT_MACBOOK  = By.xpath("//a[normalize-space()='MacBook air']");
    private static final By ALL_PRODUCTS     = By.cssSelector(".card-block .card-title");
    private static final By NEXT_BTN         = By.id("next2");

    @FindBy(xpath = "//a[@id='login2']")
    WebElement lnkLogIn;

    @FindBy(xpath = "//a[@id='nameofuser']")
    WebElement chcekText;

    @FindBy(xpath = "//a[@id='signin2']")
    WebElement lnkSignUp;

    @FindBy(xpath = "//a[@id='logout2']")
    WebElement lnkLogOut;

    @FindBy(xpath = "//a[3]")
    WebElement laptopCategory;

    @FindBy(xpath = "//a[normalize-space()='MacBook air']")
    WebElement productName;

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickLogIn() throws InterruptedException {
        logger.info("[HOME] Clicking Login button...");
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BTN));
        loginBtn.click();
        logger.info("[HOME] Login modal triggered");
    }

    public String getWelcomeText() {
        logger.info("[HOME] Fetching welcome text...");
        wait.until(ExpectedConditions.visibilityOf(chcekText));
        String text = chcekText.getText();
        logger.info("[HOME] Welcome text: " + text);
        return text;
    }

    public void clickSignUp() throws InterruptedException {
        logger.info("[HOME] Clicking Sign Up button...");
        lnkSignUp.click();
    }

    public void clickLogOut() throws InterruptedException {
        logger.info("[HOME] Clicking Logout button...");
        lnkLogOut.click();
        logger.info("[HOME] Logged out successfully");
    }

    public void clickCategory() throws InterruptedException {
        logger.info("[HOME] Clicking Laptop category...");
        laptopCategory.click();
    }

    public String getProductDetails() throws InterruptedException {
        logger.info("[HOME] Fetching product details...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_MACBOOK));
        String product = productName.getText();
        logger.info("[HOME] Product found: " + product);
        return product;
    }

    public void clickLaptopProduct() throws InterruptedException {
        logger.info("[HOME] Clicking on MacBook air product...");
        productName.click();
    }

    public void scrollToBottom() throws InterruptedException {
        logger.info("[HOME] Scrolling to bottom of page...");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(2000);
        logger.info("[HOME] Scroll complete");
    }

    public List<String> getAllProductNames() throws InterruptedException {
        List<String> allProducts = new ArrayList<>();
        boolean hasNext = true;
        int pageCount = 1;

        while (hasNext) {
            logger.info("[HOME] Collecting products from page: " + pageCount);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ALL_PRODUCTS));
            List<WebElement> products = driver.findElements(ALL_PRODUCTS);
            for (WebElement p : products) {
                allProducts.add(p.getText());
            }
            logger.info("[HOME] Products collected so far: " + allProducts.size());

            List<WebElement> nextBtnList = driver.findElements(NEXT_BTN);
            if (!nextBtnList.isEmpty()) {
                WebElement nextBtn = nextBtnList.get(0);
                if (nextBtn.isDisplayed() && nextBtn.isEnabled()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextBtn);
                    wait.until(ExpectedConditions.elementToBeClickable(nextBtn));
                    nextBtn.click();
                    Thread.sleep(2000);
                    pageCount++;
                } else {
                    logger.info("[HOME] Last page reached");
                    hasNext = false;
                }
            } else {
                logger.info("[HOME] No next button found — end of products");
                hasNext = false;
            }
        }

        logger.info("[HOME] Total products collected: " + allProducts.size());
        return allProducts;
    }

    public void addProductsToCart(String products) throws InterruptedException {
        logger.info("[HOME] Adding product to cart: " + products);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[normalize-space()='" + products + "']")));
        driver.findElement(By.linkText(products)).click();
        logger.info("[HOME] Product clicked: " + products);
    }
}