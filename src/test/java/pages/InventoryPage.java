package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.PrivateKey;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InventoryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    //locators
    private By productTitles = By.className("inventory_item_name");
    private By productPrice = By.className("inventory_item_price");
    private By sortDropdown  = By.className("product_sort_container");
    private By addToCartButton = By.xpath("(//button[text()='Add to cart'])[1]");
    private By cartBadge = By.className("shopping_cart_badge");
    private By inventoryList = By.className("inventory_list");

    //Constructors
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public int getProductCount() {
        return driver.findElements(productTitles).size();
    }

    //return product names as a list of Strings
    public List<String> getProductNames() {
        List<WebElement> elements = driver.findElements(productTitles); //Get all web elements matching the locator

        List<String> productNames = new ArrayList<>();
        for (WebElement element : elements){
            productNames.add(element.getText());
        }
        return productNames;
    }

    //return product prices
    public List<Double> getProductPrices() {
        List<WebElement> elements = driver.findElements(productPrice);

        List<Double> productPrices = new ArrayList<>();
        for (WebElement element : elements){
            String priceText = element.getText().replace("$", "");
            productPrices.add(Double.parseDouble(priceText));
        }
        return productPrices;
    }

    //select sorting option from dropdown
    public void sortBy(String option){
        WebElement dropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(sortDropdown)
        );

        Select select = new Select(dropdown);
        select.selectByVisibleText(option);
    }

    //Add 1st product tto cart
    public void addFirstProductToCart(){
        driver.findElement(addToCartButton).click();
    }

    //returns cart count number
    public int getCartCount() {
        return Integer.parseInt(
                wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge))
                        .getText()
        );
    }

    //for inventory page fully load
    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryList));
    }


}
