//This page knows how to interact with the login page
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    public WebDriver driver;
    private WebDriverWait wait;

    //locators
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector("[data-test='error']");
    private By productsTitle = By.className("title");

    // --- CONSTRUCTOR ---
    // Receives the browser from the test and sets up a 10-second wait
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //--Actions--
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    // Convenience method — completes full login in one call
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    //--verifications--
    public String getPageTitle() {
        return wait.until(ExpectedConditions
                .visibilityOf(driver.findElement(productsTitle)))
                .getText();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions
                .visibilityOf(driver.findElement(errorMessage)))
                .getText();
    }





}
