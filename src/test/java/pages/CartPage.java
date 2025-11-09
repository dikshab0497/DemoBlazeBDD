package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage{
	
	public CartPage(WebDriver driver) {
		super(driver);
	}
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	@FindBy(xpath="//td[normalize-space()='MacBook air']")
	WebElement productDetails;
	
	@FindBy(xpath="//a[normalize-space()='Delete']")
	WebElement LnkprodDelete;
	
	@FindBy(xpath="//h3[@id='totalp']")
	WebElement totalAmount;
	
	
	public String getProductFromAddToCart() throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[normalize-space()='MacBook air']")));
		return productDetails.getText();
	}
	
	public void deleteProductFromCart() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Delete']")));
		LnkprodDelete.click();
	}
	
	public List<WebElement> validateProductDeletion(String deletedProduct) {	
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
		        By.xpath("//td[normalize-space()='MacBook air']")
		    ));
		return driver.findElements(By.xpath("//td[normalize-space()='" + deletedProduct + "']"));
	}
	
	public String getTotalAmount() throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@id='totalp']")));
		return totalAmount.getText();
	}
	
}
