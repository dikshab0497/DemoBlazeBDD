package StepDefinitions;

import io.cucumber.java.en.Given;
import pages.LaptopProductsPage;
import pages.PlaceOrderPage;
import testBase.BaseClass;

public class PlaceOrderStepDefinition extends BaseClass{
	
	PlaceOrderPage placeOrderPage;
	
	@Given("User Enter Deatils on Purchase Order Form")
    public void enterDetailsOnOurchaseOrderForm() throws InterruptedException {
       
		placeOrderPage = new PlaceOrderPage(driver);

		placeOrderPage.enterPlaceOrderDetails();
		
	}
	
	@Given("User Enter On Alert Box that speficies Data missing")
    public void enterAlertBoxForMissingData() throws InterruptedException {
       
		placeOrderPage = new PlaceOrderPage(driver);

		placeOrderPage.clickOnAddToCartAlertBox();
		
	}

	


}
