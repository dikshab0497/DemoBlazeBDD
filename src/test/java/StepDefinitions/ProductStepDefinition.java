package StepDefinitions;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import pages.CartPage;
import pages.HomePage;
import pages.LaptopProductsPage;
import testBase.BaseClass;
import utilities.ExcelUtility;
import utilities.ScenarioContextGlobalDataUtility;

public class ProductStepDefinition extends BaseClass {

    private static final By PRODUCT_CARD_TITLES = By.cssSelector(".card-block .card-title");
    private static final By PRODUCT_HEADER      = By.cssSelector(".name");

    LaptopProductsPage laptopProductsPage;
    HomePage homePage;
    CartPage cartPage;

    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

    @Given("User check selected product is display")
    public void validateProductFromCategory() {
        logger.info("[PRODUCT-STEP] Validating selected product is displayed...");
        laptopProductsPage = new LaptopProductsPage(getDriver());

        String productName = laptopProductsPage.getProductDetailsFromSelectedCategory();
        String keyword = "MacBook air";

        logger.info("[PRODUCT-STEP] Product found: " + productName);
        logger.info("[PRODUCT-STEP] Asserting contains keyword: " + keyword);

        Assert.assertTrue(
                productName.contains(keyword),
                "Product '" + productName + "' is present in Product page"
        );
        logger.info("[PRODUCT-STEP] Product display assertion passed ✅");
    }

    @Given("User click AddToCartButton")
    public void clickOnAddToCartBtn() {
        logger.info("[PRODUCT-STEP] Clicking Add to Cart...");
        laptopProductsPage = new LaptopProductsPage(getDriver());
        laptopProductsPage.clickOnAddToCart();
        laptopProductsPage.clickOnAddToCartAlertBox();
        logger.info("[PRODUCT-STEP] Product added to cart successfully");
    }

    @Given("User click on Cart Link")
    public void clickOnCartLink() {
        logger.info("[PRODUCT-STEP] Navigating to Cart...");
        laptopProductsPage = new LaptopProductsPage(getDriver());
        laptopProductsPage.clickOnCart();
        logger.info("[PRODUCT-STEP] Cart page opened");
    }

    @Given("User validates product details on Product Page")
    public void validateProductDetailsOnProductPage() {
        logger.info("[PRODUCT-STEP] Validating product details on product page...");
        laptopProductsPage = new LaptopProductsPage(getDriver());

        String expectedName        = "MacBook air";
        String expectedPrice       = "$700 *includes tax";
        String expectedDescription = "1.6GHz dual-core Intel Core i5 (Turbo Boost up to 2.7GHz) with 3MB shared L3 cache Configurable to 2.2GHz dual-core Intel Core i7 (Turbo Boost up to 3.2GHz) with 4MB shared L3 cache.";

        String actualName        = laptopProductsPage.getProductName();
        String actualPrice       = laptopProductsPage.getProductPrice();
        String actualDescription = laptopProductsPage.getProductDescription();

        logger.info("[PRODUCT-STEP] Expected Name : " + expectedName  + " | Actual: " + actualName);
        logger.info("[PRODUCT-STEP] Expected Price: " + expectedPrice + " | Actual: " + actualPrice);

        Assert.assertEquals(actualName, expectedName, "Product name mismatch!");
        Assert.assertEquals(actualPrice, expectedPrice, "Product price mismatch!");
        Assert.assertTrue(actualDescription.contains(expectedDescription), "Product description mismatch!");

        logger.info("[PRODUCT-STEP] Product details validation passed ✅");
    }

    @Given("User Place an Order")
    public void clickOnbtnPlaceOrder() {
        logger.info("[PRODUCT-STEP] Clicking Place Order button...");
        laptopProductsPage = new LaptopProductsPage(getDriver());
        laptopProductsPage.clickOnPlaceOrderbtn();
        logger.info("[PRODUCT-STEP] Place Order button clicked");
    }

    @Given("User add multiple products from Excel")
    public void addMultipleProductsToCart() throws IOException {
        logger.info("[PRODUCT-STEP] Reading products from Excel...");
        ExcelUtility excel = new ExcelUtility("src/test/resources/testdata/ProductNames.xlsx");
        int rowCount = excel.getRowCount("ProductName");

        List<String> products = new ArrayList<>();
        for (int i = 1; i <= rowCount; i++) {
            products.add(excel.getCellData("ProductName", i, 0));
        }
        logger.info("[PRODUCT-STEP] Products to add: " + products);

        homePage = new HomePage(getDriver());
        laptopProductsPage = new LaptopProductsPage(getDriver());

        int totalAmount = 0;

        for (String productName : products) {
            logger.info("[PRODUCT-STEP] Adding product to cart: " + productName);

            homePage.clickCategory();
            // ✅ wait for product cards to load instead of Thread.sleep(500)
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_CARD_TITLES));

            homePage.addProductsToCart(productName);
            // ✅ wait for product page to load instead of Thread.sleep(500)
            wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_HEADER));

            String strAmount = laptopProductsPage.getProductPrice();
            logger.info("[PRODUCT-STEP] Product price: " + strAmount);
            // ✅ removed Thread.sleep(500) — getProductPrice() already has its own wait

            laptopProductsPage.clickOnAddToCart();
            // ✅ removed Thread.sleep(500) — clickOnAddToCartAlertBox() waits for alert
            laptopProductsPage.clickOnAddToCartAlertBox();

            // ✅ wait for home link to be clickable instead of implicit delay
            laptopProductsPage.clickOnHome();
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(PRODUCT_CARD_TITLES));

            int price = Integer.parseInt(strAmount.replaceAll("[^0-9]", ""));
            totalAmount += price;
            logger.info("[PRODUCT-STEP] Running total: " + totalAmount);
        }

        logger.info("[PRODUCT-STEP] All products added. Expected total: " + totalAmount);
        ScenarioContextGlobalDataUtility.setExpectedTotal(totalAmount);
    }

    @Given("User Validate total amount")
    public void validateTotalPrice() throws InterruptedException {
        logger.info("[PRODUCT-STEP] Validating total cart amount...");
        cartPage = new CartPage(getDriver());

        String strTotalPrice = cartPage.getTotalAmount();
        int totalPrice = Integer.parseInt(strTotalPrice);

        logger.info("[PRODUCT-STEP] Expected total: " + ScenarioContextGlobalDataUtility.getExpectedTotal());
        logger.info("[PRODUCT-STEP] Actual total  : " + totalPrice);

        Assert.assertEquals(ScenarioContextGlobalDataUtility.getExpectedTotal(), totalPrice,
                "Total Price is not matching");
        logger.info("[PRODUCT-STEP] Total amount assertion passed ✅");
    }

    @Given("User scroll the page till particular product")
    public void scrollPageTillProduct() {
        logger.info("[PRODUCT-STEP] Scrolling page to specific product...");
        laptopProductsPage = new LaptopProductsPage(getDriver());
        laptopProductsPage.scrollPageTillProduct();
        logger.info("[PRODUCT-STEP] Scroll to product complete");
    }
}