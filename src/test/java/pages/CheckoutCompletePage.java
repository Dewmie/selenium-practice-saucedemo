package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutCompletePage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By ConfirmMessage = By.className("complete-header");

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitForPage() {
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(ConfirmMessage));
    }

    public String getConfirmationMessage() {
        waitForPage();
        return driver.findElement(ConfirmMessage).getText();
    }
}