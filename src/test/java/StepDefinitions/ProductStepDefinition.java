package StepDefinitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import pages.CartPage;
import pages.HomePage;
import pages.LaptopProductsPage;
import pages.PlaceOrderPage;
import testBase.BaseClass;
import utilities.ExcelUtility;
import utilities.ScenarioContextGlobalDataUtility;

public class ProductStepDefinition extends BaseClass{
	
	LaptopProductsPage laptopProductsPage;
	HomePage homePage;
	CartPage cartpage;
	
	@Given("User check selected product is display")
    public void validateProductFromCategory() throws InterruptedException {
       
		laptopProductsPage = new LaptopProductsPage(driver);

        String productName = laptopProductsPage.getProductDetailsFromSelectedCategory();
        String keyword = "MacBook air";
        
        Assert.assertTrue(
        	    productName.contains(keyword),
        	    "Product '" + productName + "' is present in Product page"
        	);
    }


	@Given("User click AddToCartButton")
    public void clickOnAddToCartBtn() throws InterruptedException {
       
		laptopProductsPage = new LaptopProductsPage(driver);

		laptopProductsPage.clickOnAddToCart();
		laptopProductsPage.clickOnAddToCartAlertBox();
    }

	@Given("User click on Cart Link")
    public void clickOnCartLink() throws InterruptedException {
       
		laptopProductsPage = new LaptopProductsPage(driver);

		laptopProductsPage.clickOnCart();
		
	}
	
	@Given("User validates product details on Product Page")
	public void validateProductDetailsOnProductPage() {
		laptopProductsPage = new LaptopProductsPage(driver);


	    String expectedName = "MacBook air";
	    String expectedPrice = "$700 *includes tax";
	    String expectedDescription = "1.6GHz dual-core Intel Core i5 (Turbo Boost up to 2.7GHz) with 3MB shared L3 cache Configurable to 2.2GHz dual-core Intel Core i7 (Turbo Boost up to 3.2GHz) with 4MB shared L3 cache."; // Expected description if known

	    String actualName = laptopProductsPage.getProductName();
	    String actualPrice = laptopProductsPage.getProductPrice();
	    String actualDescription = laptopProductsPage.getProductDescription();

	    Assert.assertEquals(actualName, expectedName, "Product name mismatch!");
	    Assert.assertEquals(actualPrice, expectedPrice, "Product price mismatch!");
	    Assert.assertTrue(actualDescription.contains(expectedDescription), "Product description mismatch!");
	}
	
	@Given("User Place an Order")
    public void clickOnbtnPlaceOrder() throws InterruptedException {
       
		laptopProductsPage = new LaptopProductsPage(driver);

		laptopProductsPage.clickOnPlaceOrderbtn();
		
	}
	
	@Given("User add multiple products from Excel")
    public void addMultipleProductsToCart() throws InterruptedException, IOException {
		
		ExcelUtility excel = new ExcelUtility("src/test/resources/testdata/ProductNames.xlsx");
	    int rowCount = excel.getRowCount("ProductName");
	    List<String> products = new ArrayList<>();
	    int totalAmount = 0;
	    for(int i=1; i<=rowCount; i++) {
	        products.add(excel.getCellData("ProductName", i, 0));
	    }
       
        homePage = new HomePage(driver);
    	laptopProductsPage = new LaptopProductsPage(driver);
        
        for(String ProductName:products) {
        	
        	homePage.clickCategory();
        	Thread.sleep(500);
            homePage.addProductsToCart(ProductName);
            Thread.sleep(500);
            String strAmount = laptopProductsPage.getProductPrice();
            Thread.sleep(500);
            laptopProductsPage.clickOnAddToCart();
            Thread.sleep(500);
            laptopProductsPage.clickOnAddToCartAlertBox();
            laptopProductsPage.clickOnHome();	
            
            String numberOnly = strAmount.replaceAll("[^0-9]", "");
            int price = Integer.parseInt(numberOnly);
            
            totalAmount = totalAmount + price;
        }
        System.out.println(totalAmount);
        
        ScenarioContextGlobalDataUtility.expectedTotal = totalAmount; 
        

        
    }

	@Given("User Validate total amount")
    public void validateTotalPrice() throws InterruptedException {
       
		cartpage = new CartPage(driver);
		
		String strTotalPrice  = cartpage.getTotalAmount();
		
		int totalPrice = Integer.parseInt(strTotalPrice);
		
		
		Assert.assertEquals(ScenarioContextGlobalDataUtility.expectedTotal, totalPrice, "Total Price is not matching");
		
      }
	
	@Given("User scroll the page till particular product")
    public void scrollPageTillProduct() throws InterruptedException {
       
		laptopProductsPage = new LaptopProductsPage(driver);

		laptopProductsPage.scrollPageTillProduct();
		
	}



}
