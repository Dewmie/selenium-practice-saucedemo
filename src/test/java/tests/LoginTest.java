//This page Decides WHAT scenarios to test & verifies results
package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws IOException {
        List<Object[]> data = new ArrayList<>();

        BufferedReader reader = new BufferedReader(
                new FileReader("src/test/resources/login.csv")
        );

        reader.readLine();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(",");
            data.add(row);
        }
        reader.close();
        return data.toArray(new Object[0][]);
    }

    // Runs once per row in CSV — 4 rows = 4 test runs
    @Test(dataProvider = "loginData")
    public void verifyValidLoginSuccess(String username,
                                        String password,
                                        String expectedResult,
                                        String loginShouldSucceed
    ) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        // ASSERT — verify we ended up on the right page
        if (loginShouldSucceed.equals("true")) {
            Assert.assertEquals(
                    loginPage.getPageTitle(), "Products",
                    username + " should land on Products page");
        } else {
            // Invalid login — check error message
            Assert.assertTrue(
                    loginPage.getErrorMessage()
                            .contains(expectedResult),
                    "Error should contain: " + expectedResult);
        }
    }

//    @Test
//    public void verifyLoginFailsWithWrongPassword(){
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.login("standard_user", "wrong_password");
//
//        // ASSERT — an error message should appear
//        Assert.assertTrue(
//                loginPage.getErrorMessage().contains("Username and password do not match"),
//                "Error message should appear for wrong credentials"
//        );
//    }
//
//    @Test
//    public void verifyLoginFailsWithWrongUsername(){
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.login("wrong_user", "secret_sauce");
//
//        Assert.assertTrue(
//                loginPage.getErrorMessage().contains("Username and password do not match"),
//                "Error message should appear fro wrong credentials"
//        );
//    }
//
//    @Test
//    public void verifyLoggingFailsWithEmptyField(){
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.clickLogin();
//
//        Assert.assertTrue(
//                loginPage.getErrorMessage().contains("Username is required"),
//                "Error message should appear for empty fields"
//        );
//    }


}
