package pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath="//div[@id='logInModal']//div[@class='modal-header']")
	WebElement modalLogin;
	
	@FindBy(id="loginusername")
	WebElement usernameLogin;
	
	@FindBy(id="loginpassword")
	WebElement passwordLogin;
	
	@FindBy(xpath="//button[contains(text(),'Log in')]")
	WebElement btnLogin;
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	
	public void enterLoginCredentials(String username, String password) throws InterruptedException  {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
                
        usernameLogin.sendKeys(username);
        
        Thread.sleep(500); 
    	passwordLogin.sendKeys(password);
    	
    	Thread.sleep(500); 
    	
    	btnLogin.click();
    	
    	Thread.sleep(500); 
	}
	
	public void validateAlertBox(){

		wait.until(ExpectedConditions.alertIsPresent());

		Alert alert = driver.switchTo().alert();

		System.out.println("Alert message: " + alert.getText());

		alert.accept();
	}

	public void enterLoginDetailsWithKeyboardActions(String username, String password) throws InterruptedException  {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
        
        Actions act = new Actions(driver);
        act.click(usernameLogin)
           .sendKeys(username)
           .sendKeys(Keys.TAB)
           .sendKeys(password)
           .perform();
      
    	Thread.sleep(500); 
    	btnLogin.click();
    	Thread.sleep(500); 
	}

	
	
}
