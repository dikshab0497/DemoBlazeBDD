package StepDefinitions;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import pages.ConfirmationPage;
import testBase.BaseClass;

public class ConfirmationStepDefinition extends BaseClass {

    ConfirmationPage confirmationPage;

    @Given("User Validate Details From confirmation Screen")
    public void validateDetailsFromConfmScreen() throws InterruptedException {
        logger.info("[CONFIRMATION-STEP] Fetching purchase order details...");
        confirmationPage = new ConfirmationPage(getDriver());

        String purchaseProductDetail = confirmationPage.getPurchaseOrderDetails();
        logger.info("[CONFIRMATION-STEP] Order details received: " + purchaseProductDetail);

        logger.info("[CONFIRMATION-STEP] Asserting Amount...");
        Assert.assertTrue(purchaseProductDetail.contains("Amount: 700 USD"),
                "Amount validation failed in order details");
        logger.info("[CONFIRMATION-STEP] Amount assertion passed");

        logger.info("[CONFIRMATION-STEP] Asserting Card Number...");
        Assert.assertTrue(purchaseProductDetail.contains("Card Number: 56789087690"),
                "Card Number validation failed in order details");
        logger.info("[CONFIRMATION-STEP] Card Number assertion passed");

        logger.info("[CONFIRMATION-STEP] Asserting Name...");
        Assert.assertTrue(purchaseProductDetail.contains("Name: Memo"),
                "Name validation failed in order details");
        logger.info("[CONFIRMATION-STEP] Name assertion passed");

        logger.info("[CONFIRMATION-STEP] All confirmation assertions passed ✅");
    }
}