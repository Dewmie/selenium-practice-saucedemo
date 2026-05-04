package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.ConfigReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EndToEndTest extends BaseTest {

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

    @Test(dataProvider = "checkoutData")
    public void verifySuccessfulPurchase(String firstname,
                                         String lastname,
                                         String zipcode) {

        test.get().info("Step 1: Login with valid credentials");

        //--login--
        LoginPage loginPage = new LoginPage(driver);
        //loginPage.login("standard_user","secret_sauce");

        loginPage.login(
                utils.ConfigReader.getValidUsername(),
                utils.ConfigReader.getValidPassword()
        );

        Assert.assertEquals(loginPage.getPageTitle()
                , "Products"
                , "Page title should be 'Products after successful login'");

        //--add product to cart--
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.waitForPageLoad();

        test.get().info("Step 2: Adding first product to cart");
        String productName = inventoryPage.getProductNames().get(0);         //--get the 1st product name before adding to cart--
        inventoryPage.addFirstProductToCart();

        test.get().info("Product added: " + productName);


        Assert.assertEquals(inventoryPage.getCartCount(), 1,
                "Cart count should be 1 after adding 1 product");

        test.get().info("Step 3: Going to cart");

        //--go to cart page by click cart icon--
        driver.findElement(By.className("shopping_cart_link")).click();

        CartPage cartPage = new CartPage(driver);
        cartPage.waitForPageLoad(); //wait for cart page load

        Assert.assertEquals(cartPage.getCartItemName(), productName,
                "The product added to cart should appear in cart");

        test.get().info("Step 4: Checkout - filling form");


        //--Checkout form--
        //cartPage.clickCheckoutButton();

//        CheckoutPage checkoutPage = new CheckoutPage(driver);
//        checkoutPage.fillCheckoutForm("Peter","Parker","81000");

//        Assert.assertTrue(checkoutPage.checkTotalPrice().contains("$"),
//                "Order summary should include the total price");
//
//        checkoutPage.clickFinish();
//
//        Assert.assertTrue(checkoutPage.getConfirmMessage().contains("Thank you"));


        //----------CHECKOUT FORM-------------------

        // --- PART 1 -----
        cartPage.clickCheckoutButton();

        CheckoutStepTwoPage stepTwo = new CheckoutStepOnePage(driver)
                .fillForm(firstname, lastname, zipcode);

        test.get().info("Step 5: Verifying order summary");

        //---PART 2----
        Assert.assertTrue(
                stepTwo.getTotalPrice().contains("$"),
                "Summary should show total price");

        CheckoutCompletePage stepThree = stepTwo.clickFinish();

        test.get().info("Step 6: Verifying confirmation");

        //----PART 3----
        Assert.assertEquals(
                stepThree.getConfirmationMessage(),
                "Thank you for your order!",
                "Should show order confirmation");

    }

}
