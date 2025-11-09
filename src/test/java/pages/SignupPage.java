package pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage extends BasePage {
	
	public SignupPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(id="sign-username")
	WebElement usernameLogin;
	
	@FindBy(id="sign-password")
	WebElement passwordLogin;
	
	@FindBy(xpath="//button[normalize-space()='Sign up']")
	WebElement btnSignUp;
	

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	
	public void enterSignUpCredentials(String username, String password) throws InterruptedException  {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign-username")));
                
//        usernameLogin.sendKeys(username);
        usernameLogin.sendKeys("");
    	passwordLogin.sendKeys(password);
    	   	
    	btnSignUp.click();
    	
	}
	
	public String validateSuccessfulAlertBox(){

		wait.until(ExpectedConditions.alertIsPresent());

		Alert alert = driver.switchTo().alert();

		System.out.println("Alert message: " + alert.getText());
		String text = alert.getText();
		alert.accept();
		
		
	    return text;
	}
	
	public String validateWarningAlertBox(){

		wait.until(ExpectedConditions.alertIsPresent());

		Alert alert = driver.switchTo().alert();

		System.out.println("Alert message: " + alert.getText());
		String text = alert.getText();
		alert.accept();
		
		
	    return text;
	}

	
	


}
