package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

public class CartTest extends BaseTest {

    private String expectedProductName;

    @BeforeMethod
    public void setUpCartTest() {
        LoginPage loginPage = new LoginPage(driver);

        //loginPage.login("standard_user", "secret_sauce");

        loginPage.login(
                utils.ConfigReader.getValidUsername(),
                utils.ConfigReader.getValidPassword());

        //add 1st product to cart
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.waitForPageLoad();
        expectedProductName = inventoryPage.getProductNames().get(0);
        inventoryPage.addFirstProductToCart();

        //go to cart page by click cart icon
        driver.findElement(By.className("shopping_cart_link")).click();

        new CartPage(driver).waitForPageLoad(); //wait for cart page load

    }

    //Test 1 - verify correct product appears in cart
    @Test
    public void verifyProductInCart() {
        CartPage cartPage = new CartPage(driver);
        String actualName = cartPage.getCartItemName();

        test.get().info("Expected product: " + expectedProductName);
        test.get().info("Actual product: " + actualName);

        Assert.assertEquals(actualName, "Sauce Labs Backpack",
                "The product added to cart should appear in cart");
    }

    //Test 2 - remove product update
    @Test
    public void verifyRemoveProductFromCart() {
        CartPage cartPage = new CartPage(driver);
        cartPage.removeFirstProductFromCart();

        test.get().info("Product removed");


        Assert.assertFalse(cartPage.isCartBadgeDisplayed(),
                "Cart badge should not display after remove the item");
    }

    //Test 3 - verify checkout button navigation
    @Test
    public void verifyCheckoutButtonNavigation() {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckoutButton();

        String currentURL = cartPage.getCurrentUrl();
        test.get().info("Navigated to: " + currentURL + " successfully");

        Assert.assertTrue(currentURL.contains("checkout-step-one"),
                "Click Checkout button should navigate to checkout page");
    }


}
