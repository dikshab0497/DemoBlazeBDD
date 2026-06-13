package StepDefinitions;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import pages.CartPage;
import testBase.BaseClass;

public class CartStepDefinition extends BaseClass {

    CartPage cartPage;

    @Given("User Validate product in Cart")
    public void validateProductPresentInCart() throws InterruptedException {
        logger.info("[CART-STEP] Validating product in cart...");
        cartPage = new CartPage(getDriver());

        String productName = cartPage.getProductFromAddToCart();
        String keyword = "MacBook air";

        logger.info("[CART-STEP] Product found: " + productName);
        logger.info("[CART-STEP] Asserting product contains: " + keyword);

        Assert.assertTrue(
                productName.contains(keyword),
                "Product '" + productName + "' is present in Cart page"
        );
        logger.info("[CART-STEP] Assertion passed — product is present in cart");
    }

    @Given("User Delete Product from Cart")
    public void deleteProductFromCart() throws InterruptedException {
        logger.info("[CART-STEP] Deleting product from cart...");
        cartPage = new CartPage(getDriver());
        cartPage.deleteProductFromCart();
        logger.info("[CART-STEP] Product deleted from cart");
    }

    @Given("User Validate Product is Deleted")
    public void validateProductDeletionFromCart() throws InterruptedException {
        logger.info("[CART-STEP] Validating product deletion...");
        cartPage = new CartPage(getDriver());

        String productName = "MacBook air";
        List<WebElement> products = cartPage.validateProductDeletion(productName);

        logger.info("[CART-STEP] Remaining items with name '" + productName + "': " + products.size());

        Assert.assertTrue(
                products.size() == 0,
                "Product is still present in cart: " + productName
        );
        logger.info("[CART-STEP] Assertion passed — product successfully deleted from cart");
    }
}