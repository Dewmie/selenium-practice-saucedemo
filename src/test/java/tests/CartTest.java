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

    @BeforeMethod
    public void setUpCartTest()
    {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        //add 1st product to cart
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.waitForPageLoad();
        inventoryPage.addFirstProductToCart();

        //go to cart page by click cart icon
        driver.findElement(By.className("shopping_cart_link")).click();

        new CartPage(driver).waitForPageLoad(); //wait for cart page load

    }

    //Test 1 - verify correct product appears in cart
    @Test
    public void verifyProductInCart(){
        CartPage cartPage = new CartPage(driver);
        String actualName = cartPage.getCartItemName();

        Assert.assertEquals(actualName, "Sauce Labs Backpack",
                "The product added to cart should appear in cart");
    }

    //Test 2 - remove product update
    @Test
    public void verifyRemoveProductFromCart(){
        CartPage cartPage = new CartPage(driver);
        cartPage.removeFirstProductFromCart();

        Assert.assertFalse(cartPage.isCartBadgeDisplayed(),
                "Cart badge should not display after remove the item");
    }

    //Test 3 - verify checkout button navigation
    @Test
    public void verifyCheckoutButtonNavigation(){
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckoutButton();

        Assert.assertTrue(cartPage.getCurrentUrl().contains("checkout-step-one"),
                "Click Checkout button should navigate to checkout page");
    }


}
