package pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PlaceOrderPage extends BasePage{
	
	public PlaceOrderPage(WebDriver driver) {
		super(driver);
	}

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	@FindBy(xpath="//input[@id='name']")
	WebElement fieldName;
	
	@FindBy(xpath="//input[@id='country']")
	WebElement fieldCountry;
	
	@FindBy(xpath="//input[@id='city']")
	WebElement fieldCity;
	
	@FindBy(xpath="//input[@id='card']")
	WebElement fieldCreditCard;
	
	@FindBy(xpath="//input[@id='month']")
	WebElement fieldMonth;
	
	@FindBy(xpath="//input[@id='year']")
	WebElement fieldYear;
	
	@FindBy(xpath="//button[normalize-space()='Purchase']")
	WebElement btnPurchase;
	
	
	public void enterPlaceOrderDetails() throws InterruptedException  {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
                
    	fieldName.sendKeys("");
    	fieldCountry.sendKeys("");
    	fieldCity.sendKeys("");
    	fieldCreditCard.sendKeys(" ");
    	fieldMonth.sendKeys("11");
    	fieldYear.sendKeys("27");
   
    	btnPurchase.click();
    	
	}
	
	public void clickOnAddToCartAlertBox(){

		wait.until(ExpectedConditions.alertIsPresent());

		Alert alert = driver.switchTo().alert();

		System.out.println("Alert message: " + alert.getText());

		alert.accept();
	}


	
}
