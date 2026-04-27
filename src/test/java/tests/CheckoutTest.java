package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

public class CheckoutTest extends BaseTest {

    @BeforeMethod
    public void setupCheckoutTest(){
        new LoginPage(driver).login("standard_user", "secret_sauce");

        //add product to cart
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.waitForPageLoad();
        inventoryPage.addFirstProductToCart();

        //click cart icon
        driver.findElement(By.className("shopping_cart_link")).click();

        CartPage cartPage = new CartPage(driver);
        cartPage.waitForPageLoad();

        //click checkout
        cartPage.clickCheckoutButton();

        new CheckoutStepOnePage(driver).waitForPage();
    }

    //Test 1 - Filling checkout form with empty fields showing an error
    @Test
    public void emptyFieldErrorMessageCheck(){
        CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage(driver);
        checkoutStepOnePage.clickContinueEmpty();

        Assert.assertTrue(checkoutStepOnePage.getErrorMessage()
                        .contains("Error"),
                "Empty form should show error message");
    }

    //Test 2 - Valid form must show order details with total price
    @Test
    public void checkOrderDetails(){

        CheckoutStepOnePage stepOne = new CheckoutStepOnePage(driver);
        CheckoutStepTwoPage stepTwo = stepOne.fillForm("Peter", "Parker", "12345");

        //return step 2 page
        Assert.assertTrue(stepTwo.getTotalPrice().contains("$"),
                "Total price should be displayed on overview page");
    }

    //Test 3 - Check Confirmation message
    @Test
    public void checkConfirmationMessage(){
        // Step 1 → Step 2
        CheckoutStepTwoPage stepTwo = new CheckoutStepOnePage(driver)
                .fillForm("Peter", "Parker", "12345");

        // Step 2 → Step 3
        CheckoutCompletePage stepThree = stepTwo.clickFinish();

        Assert.assertEquals(
                stepThree.getConfirmationMessage(),
                "Thank you for your order!",
                "Confirmation page should show thank you message");

    }


}
