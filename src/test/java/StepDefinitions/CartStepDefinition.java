package StepDefinitions;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import pages.CartPage;
import testBase.BaseClass;

public class CartStepDefinition extends BaseClass {
	
	CartPage cartpage;

	@Given("User Validate product in Cart")
    public void validateProductPresentInCart() throws InterruptedException {
       
		cartpage = new CartPage(driver);

        String productName = cartpage.getProductFromAddToCart();
        String keyword = "MacBook air";
        
        Assert.assertTrue(
        	    productName.contains(keyword),
        	    "Product '" + productName + "' is present in Cart page"
        	);
    }

	@Given("User Delete Product from Cart")
    public void deleteProductFromCart() throws InterruptedException {
       
		cartpage = new CartPage(driver);
		
		cartpage.deleteProductFromCart();

      }
	
	@Given("User Validate Product is Deleted")
    public void validateProductDeletionFromCart() throws InterruptedException {
       
		cartpage = new CartPage(driver);
		String productName = "MacBook air";
		
		List<WebElement> products = cartpage.validateProductDeletion(productName);
		
		Assert.assertTrue(products.size() == 0, "Product is still present in cart: " + productName);
	}
	
	



}
