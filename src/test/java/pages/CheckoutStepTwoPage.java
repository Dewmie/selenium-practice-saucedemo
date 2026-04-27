package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutStepTwoPage {

    WebDriver driver;
    WebDriverWait wait;

    private By totalPrice = By.className("summary_total_label");
    private By finishBtn = By.id("finish");
    private By cancelButton    = By.id("cancel");

    public CheckoutStepTwoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //wait for page load
    public void waitForPage() {
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(finishBtn));
    }

    public String getTotalPrice() { //get total price text
        return wait.until(ExpectedConditions.presenceOfElementLocated(totalPrice)).getText();
    }

    public CheckoutCompletePage clickFinish() {
        waitForPage();
        driver.findElement(finishBtn).click();

        CheckoutCompletePage completePage = new CheckoutCompletePage(driver);
        completePage.waitForPage(); // ← wait for Step 3 to actually load
        return completePage;
    }

    public void  clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
    }

}