package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckoutTest extends BaseTest {

    @BeforeMethod
    public void setupCheckoutTest() {
        //new LoginPage(driver).login("standard_user", "secret_sauce");
        new LoginPage(driver).login(
                utils.ConfigReader.getValidUsername(),
                utils.ConfigReader.getValidPassword()
        );

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

    //Using CSV files
    @DataProvider(name = "checkoutData")
    public Object[][] checkoutData() throws IOException {
        List<Object[]> data = new ArrayList<>();

        BufferedReader reader = new BufferedReader(
                new FileReader("src/test/resources/checkout.csv")
        );
        reader.readLine();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(",");
            data.add(row);
        }

        reader.close();
        return data.toArray(new Object[0][]);
    }

    //Test 1 - Filling checkout form with empty fields showing an error
    @Test
    public void emptyFieldErrorMessageCheck() {
        CheckoutStepOnePage stepOne = new CheckoutStepOnePage(driver);
        stepOne.clickContinueEmpty();

        String errorMsg = stepOne.getErrorMessage();
        test.get().info("Error message shown: " + errorMsg);

        Assert.assertTrue(stepOne.getErrorMessage()
                        .contains("Error"),
                "Empty form should show error message");
    }

    //Test 2 - Valid form must show order details with total price
    @Test(dataProvider = "checkoutData")
    public void checkOrderDetails(String firstname,
                                  String lastname,
                                  String zipcode) {

        //---- Log form data in report---
        test.get().info("First Name: " + firstname);
        test.get().info("Last Name: " + lastname);
        test.get().info("Zip Code: " + zipcode);

        CheckoutStepOnePage stepOne = new CheckoutStepOnePage(driver);
        CheckoutStepTwoPage stepTwo = stepOne.fillForm(firstname, lastname, zipcode);

        String totalPrice = stepTwo.getTotalPrice();
        test.get().info("Total Price: " + totalPrice);

        //return step 2 page
        Assert.assertTrue(totalPrice.contains("$"),
                "Total price should be displayed on overview page");
    }

    //Test 3 - Check Confirmation message
    @Test(dataProvider = "checkoutData")
    public void checkConfirmationMessage(String firstname,
                                         String lastname,
                                         String zipcode) {

        test.get().info("First Name: " + firstname);
        test.get().info("Last Name: " + lastname);
        test.get().info("Zip Code: " + zipcode);

        // Step 1 → Step 2
        CheckoutStepTwoPage stepTwo = new CheckoutStepOnePage(driver)
                .fillForm(firstname, lastname, zipcode);

        // Step 2 → Step 3
        CheckoutCompletePage stepThree = stepTwo.clickFinish();

        test.get().info("Confirmation message showing successfuly");

        Assert.assertEquals(
                stepThree.getConfirmationMessage(),
                "Thank you for your order!",
                "Confirmation page should show thank you message");

    }


}
