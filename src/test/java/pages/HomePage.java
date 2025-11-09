package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {
	
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	
	@FindBy(xpath="//a[@id='login2']")
	WebElement lnkLogIn;
	
	@FindBy(xpath="//a[@id='nameofuser']")
	WebElement chcekText;
	
	@FindBy(xpath="//a[@id='signin2']")
	WebElement lnkSignUp;
	
	@FindBy(xpath="//a[@id='logout2']")
	WebElement lnkLogOut;
	
	@FindBy(xpath="//a[3]")
	WebElement laptopCategory;
	
	@FindBy(xpath="//a[normalize-space()='MacBook air']")
	WebElement productName;
	
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	public void clickLogIn() throws InterruptedException {
		
		WebElement loginBtn = wait.until(
				 ExpectedConditions.elementToBeClickable(By.id("login2"))  // replace with actual id or CSS selector
			);
		
		loginBtn.click();
		
	}
	
	public String getWelcomeText() {
	    wait.until(ExpectedConditions.visibilityOf(chcekText));
	    String text = chcekText.getText();
	    System.out.println("Welcome text: " + text);
	    return text;
	}

	public void clickSignUp() throws InterruptedException {
		
		lnkSignUp.click();
	}
	
	public void clickLogOut() throws InterruptedException {
		
		lnkLogOut.click();
	}
	
	public void clickCategory() throws InterruptedException {
		
		laptopCategory.click();
	}
	
	public String getProductDetails() throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='MacBook air']")));
		return productName.getText();
	}
	
	public void clickLaptopProduct() throws InterruptedException {
		
		productName.click();
	}
	
    public void scrollToBottom() throws InterruptedException {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    	Thread.sleep(2000); 

    }
    
    public List<String> getAllProductNames() throws InterruptedException {
        List<String> allProducts = new ArrayList<>();
        boolean hasNext = true;


        while (hasNext) {
            // 1️⃣ Get all products on current page
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".card-block .card-title")));
            List<WebElement> products = driver.findElements(By.cssSelector(".card-block .card-title"));
            for (WebElement p : products) {
                allProducts.add(p.getText());
            }

            // 2️⃣ Check if Next button exists and is enabled
            List<WebElement> nextBtnList = driver.findElements(By.id("next2"));
            if (!nextBtnList.isEmpty()) {
                WebElement nextBtn = nextBtnList.get(0);

                // Check if it’s visible AND enabled
                if (nextBtn.isDisplayed() && nextBtn.isEnabled()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextBtn);
                    wait.until(ExpectedConditions.elementToBeClickable(nextBtn));
                    nextBtn.click();
                    Thread.sleep(2000); // wait for page to load
                } else {
                    hasNext = false; // last page reached
                }
            } else {
                hasNext = false; // no next button found
            }
        }

        return allProducts;
    }

    public void addProductsToCart(String products) throws InterruptedException {
        	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='" +products+ "']")));
        	driver.findElement(By.linkText(products)).click();  	        
    }
    
}
