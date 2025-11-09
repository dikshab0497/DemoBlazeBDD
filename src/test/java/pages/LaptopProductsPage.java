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
	
	public LaptopProductsPage(WebDriver driver) {
		super(driver);
	}
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	@FindBy(xpath="//h2[normalize-space()='MacBook air']")
	WebElement headerLaptopProduct;
	
	@FindBy(xpath="//a[normalize-space()='Add to cart']")
	WebElement btnAddToCart;
	
	@FindBy(xpath="//a[@id='cartur']")
	WebElement linkCart;
	
	@FindBy(xpath="//button[normalize-space()='Place Order']")
	WebElement btnPlaceOrder;
	
	@FindBy(xpath="//li[@class='nav-item active']//a[@class='nav-link']")
	WebElement linkHome;
	
	@FindBy(xpath="//a[normalize-space()='MacBook Pro']")
	WebElement scrollProd;
	
	
	
	
	
	public String getProductDetailsFromSelectedCategory() throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='MacBook air']")));
		return headerLaptopProduct.getText();
	}
	
	public void clickOnAddToCart() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Add to cart']")));
		btnAddToCart.click();
	}
	
	 	public String getProductName() {
	        return driver.findElement(By.cssSelector(".name")).getText(); // Example selector
	    }

	    public String getProductPrice() {
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@class='price-container']")));
	    	
	        return driver.findElement(By.cssSelector(".price-container")).getText(); // Example: "$360"
	    }

	    public String getProductDescription() {
	        return driver.findElement(By.cssSelector("#more-information")).getText(); // Example description
	    }
	
	
	public void clickOnAddToCartAlertBox(){

		wait.until(ExpectedConditions.alertIsPresent());

		Alert alert = driver.switchTo().alert();

		System.out.println("Alert message: " + alert.getText());

		alert.accept();
	}

	public void clickOnCart() {
		linkCart.click();
	}
	
	public void clickOnHome() {
		linkHome.click();
	}
	
	
	public void clickOnPlaceOrderbtn() {
		btnPlaceOrder.click();
	}
	
	public void scrollPageTillProduct() throws InterruptedException {
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='MacBook Pro']")));
		
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", scrollProd);

        Thread.sleep(2000);

        scrollProd.click();
	}
	
	
}
