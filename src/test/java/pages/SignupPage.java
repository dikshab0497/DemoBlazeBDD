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

    private static final By FIELD_USERNAME = By.id("sign-username");

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    @FindBy(id = "sign-username")
    WebElement usernameLogin;

    @FindBy(id = "sign-password")
    WebElement passwordLogin;

    @FindBy(xpath = "//button[normalize-space()='Sign up']")
    WebElement btnSignUp;

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    public void enterSignUpCredentials(String username, String password) throws InterruptedException {
        logger.info("[SIGNUP] Waiting for Sign Up form...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIELD_USERNAME));

        logger.info("[SIGNUP] Entering username: " + username);
        usernameLogin.sendKeys(username);

        logger.info("[SIGNUP] Entering password");
        passwordLogin.sendKeys(password);

        logger.info("[SIGNUP] Clicking Sign Up button...");
        btnSignUp.click();
    }

    public String validateSuccessfulAlertBox() {
        logger.info("[SIGNUP] Waiting for success alert...");
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        logger.info("[SIGNUP] Success alert message: " + text); // ✅ replaced System.out.println
        alert.accept();
        logger.info("[SIGNUP] Success alert accepted");
        return text;
    }

    public String validateWarningAlertBox() {
        logger.info("[SIGNUP] Waiting for warning alert...");
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        logger.info("[SIGNUP] Warning alert message: " + text); // ✅ replaced System.out.println
        alert.accept();
        logger.info("[SIGNUP] Warning alert accepted");
        return text;
    }
}