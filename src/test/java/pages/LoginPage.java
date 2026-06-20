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
import java.time.Duration;

public class LoginPage extends BasePage {

    private static final By FIELD_USERNAME = By.id("loginusername");
    private static final By FIELD_PASSWORD = By.id("loginpassword");
    private static final By WELCOME_TEXT   = By.id("nameofuser");

    @FindBy(xpath = "//div[@id='logInModal']//div[@class='modal-header']")
    WebElement modalLogin;

    @FindBy(id = "loginusername")
    WebElement usernameLogin;

    @FindBy(id = "loginpassword")
    WebElement passwordLogin;

    @FindBy(xpath = "//button[contains(text(),'Log in')]")
    WebElement btnLogin;

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterLoginCredentials(String username, String password) {
        logger.info("[LOGIN] Waiting for login form...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIELD_USERNAME));

        logger.info("[LOGIN] Entering username: " + username);
        usernameLogin.sendKeys(username);

        wait.until(ExpectedConditions.elementToBeClickable(FIELD_PASSWORD));
        logger.info("[LOGIN] Entering password");
        passwordLogin.sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(btnLogin));
        logger.info("[LOGIN] Clicking Login button");
        btnLogin.click();

        // ✅ wait for login modal to disappear first
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@id='logInModal']//div[@class='modal-header']")));
        logger.info("[LOGIN] Login modal closed");

        // ✅ then wait for welcome text with longer timeout
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(WELCOME_TEXT));
        logger.info("[LOGIN] Login successful — welcome text visible");
    }
    
    public void validateAlertBox() {
        logger.info("[LOGIN] Waiting for alert box...");
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        logger.info("[LOGIN] Alert message: " + alert.getText()); // ✅ replaced System.out.println
        alert.accept();
        logger.info("[LOGIN] Alert accepted");
    }

    public void enterLoginDetailsWithKeyboardActions(String username, String password) {
        logger.info("[LOGIN] Waiting for login form (keyboard actions)...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIELD_USERNAME));

        logger.info("[LOGIN] Performing keyboard actions for login...");
        Actions act = new Actions(driver);
        act.click(usernameLogin)
           .sendKeys(username)
           .sendKeys(Keys.TAB)
           .sendKeys(password)
           .perform();

        // ✅ wait for login button to be clickable instead of Thread.sleep(500)
        wait.until(ExpectedConditions.elementToBeClickable(btnLogin));
        logger.info("[LOGIN] Clicking Login button via keyboard action");
        btnLogin.click();

        // ✅ wait for welcome text to confirm login instead of Thread.sleep(500)
        wait.until(ExpectedConditions.visibilityOfElementLocated(WELCOME_TEXT));
        logger.info("[LOGIN] Login successful — welcome text visible");
    }
}