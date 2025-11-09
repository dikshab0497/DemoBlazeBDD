package StepDefinitions;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import pages.ConfirmationPage;
import pages.LaptopProductsPage;
import testBase.BaseClass;

public class ConfirmationStepDefinition extends BaseClass{
	
	ConfirmationPage confirmationPage;
	
	@Given("User Validate Details From confirmation Screen")
    public void validateDetailsFromConfmScreen() throws InterruptedException {
       
		confirmationPage = new ConfirmationPage(driver);

        String purchaseProductDetail = confirmationPage.getPurchaseOrderDetails();
        
        System.out.println(purchaseProductDetail);
       
//        Assert.assertTrue(purchaseProductDetail.contains("Id: 7274897"), "Id is incorrect");
        Assert.assertTrue(purchaseProductDetail.contains("Amount: 700 USD"), "Amount is incorrect");
        Assert.assertTrue(purchaseProductDetail.contains("Card Number: 56789087690"), "Card Number is incorrect");
        Assert.assertTrue(purchaseProductDetail.contains("Name: Memo"), "Name is incorrect");
    }


}
