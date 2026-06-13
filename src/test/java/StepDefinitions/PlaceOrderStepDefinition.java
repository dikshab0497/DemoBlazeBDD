package StepDefinitions;

import io.cucumber.java.en.Given;
import pages.PlaceOrderPage;
import testBase.BaseClass;

public class PlaceOrderStepDefinition extends BaseClass {

    PlaceOrderPage placeOrderPage;

    @Given("User Enter Deatils on Purchase Order Form")
    public void enterDetailsOnOurchaseOrderForm() throws InterruptedException {
        logger.info("[ORDER-STEP] Filling in Purchase Order form...");
        placeOrderPage = new PlaceOrderPage(getDriver());
        placeOrderPage.enterPlaceOrderDetails();
        logger.info("[ORDER-STEP] Purchase Order form submitted");
    }

    @Given("User Enter On Alert Box that speficies Data missing")
    public void enterAlertBoxForMissingData() throws InterruptedException {
        logger.info("[ORDER-STEP] Handling missing data alert box...");
        placeOrderPage = new PlaceOrderPage(getDriver());
        placeOrderPage.clickOnAddToCartAlertBox();
        logger.info("[ORDER-STEP] Missing data alert handled");
    }
}