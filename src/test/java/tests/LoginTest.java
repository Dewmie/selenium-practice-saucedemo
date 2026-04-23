//This page Decides WHAT scenarios to test & verifies results
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void verifyValidLoginSuccess(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // ASSERT — verify we ended up on the right page
        Assert.assertEquals(loginPage.getPageTitle()
                ,"Products"
                ,"Page title should be 'Products after successful login'");
    }

    @Test
    public void verifyLoginFailsWithWrongPassword(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "wrong_password");

        // ASSERT — an error message should appear
        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Username and password do not match"),
                "Error message should appear for wrong credentials"
        );
    }

    @Test
    public void verifyLoginFailsWithWrongUsername(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wrong_user", "secret_sauce");

        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Username and password do not match"),
                "Error message should appear fro wrong credentials"
        );
    }

    @Test
    public void verifyLoggingFailsWithEmptyField(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLogin();

        Assert.assertTrue(
                loginPage.getErrorMessage().contains("Username is required"),
                "Error message should appear for empty fields"
        );
    }
}
