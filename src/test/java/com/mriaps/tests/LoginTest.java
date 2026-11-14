package com.mriaps.tests;

import com.mriaps.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
                { "standard_user", "secret_sauce", "SUCCESS" },
                { "locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out." },
                { "problem_user", "secret_sauce", "SUCCESS" }
        };
    }

    @Test(dataProvider = "loginData")
    public void shouldHandleLoginAttempts(String username, String password, String expectedResult) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        if (expectedResult.equals("SUCCESS")) {
            Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"),
                    "User '" + username + "' should be redirected to the inventory page after login");
        } else {
            Assert.assertEquals(loginPage.getErrorMessage(), expectedResult,
                    "Error message mismatch for user '" + username + "'");
        }
    }
}