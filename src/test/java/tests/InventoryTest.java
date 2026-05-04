package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.InventoryPage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryTest extends BaseTest {

    @BeforeMethod
    public void login(){
        LoginPage loginPage = new LoginPage(driver);
        //loginPage.login("standard_user", "secret_sauce");

        loginPage.login(
                utils.ConfigReader.getValidUsername(),
                utils.ConfigReader.getValidPassword()
        );

        new InventoryPage(driver).waitForPageLoad();
    }

    //Test 1 -verify all 6 products load
    @Test
    public void verifyProductCount(){
        InventoryPage inventoryPage = new InventoryPage(driver);

        int actualCount = inventoryPage.getProductCount();

        test.get().info("Expected product count: 6");
        test.get().info("Actual product count: " + actualCount);

        Assert.assertEquals(actualCount,6,
                "Product page should display exactly 6 products");
    }

    //Test 2 - verify sort by price low to high
    @Test
    public void verifyProductsSortedByPriceLowToHigh(){
        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.sortBy("Price (low to high)");

        List<Double> actualPrices = inventoryPage.getProductPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        test.get().info("Actual prices: " + actualPrices);
        test.get().info("Prices are sorting low to high successfully");

        Assert.assertEquals(actualPrices, expectedPrices,
                "Product prices should be sorted by price low to high");
    }

    //Test 3 - verify sort by price high to low
    @Test
    public void verifyProductsSortedByPriceHighToLow(){
        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.sortBy("Price (high to low)");

        List<Double> actualPrices = inventoryPage.getProductPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices,Collections.reverseOrder());

        test.get().info("Actual prices: " + actualPrices);
        test.get().info("Products are prices sorting high to low successfully");


        Assert.assertEquals(actualPrices, expectedPrices,
                "Product prices should be sorted by price high to low");
    }

    //Test 4 - verify sort by name A to Z
    @Test
    public void verifyProductsSortedByNameAToZ(){
        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.sortBy("Name (A to Z)");

        List<String> actualNames = inventoryPage.getProductNames();
        List<String> expectedNames = new ArrayList<>(actualNames);
        Collections.sort(expectedNames, String.CASE_INSENSITIVE_ORDER);

        test.get().info("Actual names: " + actualNames);
        test.get().info("Product names are sorting A to Z successfully");

        Assert.assertEquals(actualNames, expectedNames,
                "Product names should be sorted by name A to Z");
    }

    //Test 5 - verify sort by name Z to A
    @Test
    public void verifyProductsSortedByNameZtoA(){
        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.sortBy("Name (Z to A)");

        List<String> actualNames = inventoryPage.getProductNames();
        List<String> expectedNames = new ArrayList<>(actualNames);
        Collections.sort(expectedNames, Collections.reverseOrder());

        test.get().info("Actual names: " + actualNames);
        test.get().info("Product names are sorting Z to A successfully");

        Assert.assertEquals(actualNames, expectedNames,
                "Product names should be sorted by name Z to A");
    }

    //Test 6 - Verify adding products to the cart update cart count
    @Test
    public void verifyAddProductUpdates(){
        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.addFirstProductToCart();

        int cartCount = inventoryPage.getCartCount();
        test.get().info("Cart count after adding 1 product: " + cartCount);

        Assert.assertEquals(inventoryPage.getCartCount(), 1,
                "Cart count should be 1 after adding 1 product");
    }



}
