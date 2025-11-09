package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConfirmationPage extends BasePage{
	
	public ConfirmationPage(WebDriver driver) {
		super(driver);
	}


	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	@FindBy(xpath="//p[contains(@class,'lead text-muted')]")
	WebElement txtPurchaseOrderDetails;
	
	public String getPurchaseOrderDetails() throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(@class,'lead text-muted')]")));
		return txtPurchaseOrderDetails.getText();
	}
	
}
