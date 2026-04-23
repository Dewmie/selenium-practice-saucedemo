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
        loginPage.login("standard_user", "secret_sauce");
        new InventoryPage(driver).waitForPageLoad();
    }

    //Test 1 -verify all 6 products load
    @Test
    public void verifyProductCount(){
        InventoryPage inventoryPage = new InventoryPage(driver);

        int actualCount = inventoryPage.getProductCount();

        Assert.assertEquals(actualCount,6,
                "Product page should display exactly 6 products");
    }

    //Test 2 - verify sort by price low to high
    @Test
    public void verifyProductsSortedByPriceLowToHigh(){
        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.sortBy("Price (low to high) ");

        List<Double> actualPrices = inventoryPage.getProductPrices();

        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

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

        Assert.assertEquals(actualNames, expectedNames,
                "Product names should be sorted by name Z to A");
    }

    //Test 6 - Verify adding products to the cart
    @Test
    public void verifyAddProductUpdates(){
        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.addFirstProductToCart();

        Assert.assertEquals(inventoryPage.getCartCount(), 1,
                "Cart count should be 1 after adding 1 product");
    }



}
