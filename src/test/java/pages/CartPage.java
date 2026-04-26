package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By cartItemName = By.className("inventory_item_name");
    private By removeButton = By.className("cart_button");
    private By checkoutButton = By.id("checkout");
    private By cartBadge      = By.className("shopping_cart_badge");
    private By cartList       = By.className("cart_list");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitForPageLoad(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartList));
    }

    public String getCartItemName(){  //return name of 1st item in cart
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemName)).getText();
    }

    public void removeFirstProductFromCart(){
        driver.findElement(removeButton).click();
    }

    public void clickCheckoutButton(){
        driver.findElement(checkoutButton).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
    }

    public boolean isCartBadgeDisplayed(){
        return driver.findElements(cartBadge).size() > 0;
    }

    public String getCurrentUrl(){  //Verify navigation success
        return driver.getCurrentUrl();
    }


}
