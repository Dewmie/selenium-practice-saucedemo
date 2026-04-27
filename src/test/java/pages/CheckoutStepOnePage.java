package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutStepOnePage {

    WebDriver driver;
    WebDriverWait wait;

    // Locators
    private By firstName = By.id("first-name");
    private By lastName = By.id("last-name");
    private By zipCode = By.id("postal-code");
    private By continueBtn = By.id("continue");
    private By errorMsg = By.cssSelector("[data-test='error']");

    public CheckoutStepOnePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,Duration.ofSeconds(10));
    }

    // Wait for this page 1 to load
    public void waitForPage() {
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(firstName));
    }

    // Actions
    public CheckoutStepTwoPage fillForm(String fName, String lName, String zip) {

        waitForPage();

        driver.findElement(firstName).sendKeys(fName);
        driver.findElement(lastName).sendKeys(lName);
        driver.findElement(zipCode).sendKeys(zip);

        driver.findElement(continueBtn).click();

        CheckoutStepTwoPage stepTwo = new CheckoutStepTwoPage(driver);
        stepTwo.waitForPage(); // ← wait for Step 2 before returning
        return stepTwo;
    }

    public void clickContinueEmpty() {
        waitForPage();
        driver.findElement(continueBtn).click();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions
                        .visibilityOfElementLocated(errorMsg))
                .getText();
    }
}
