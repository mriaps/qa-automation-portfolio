package com.mriaps.tests;

import com.mriaps.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @DataProvider(name = "validLoginData")
    public Object[][] validLoginData() {
        return new Object[][] {
                { "standard_user", "secret_sauce" },
                { "problem_user", "secret_sauce" }
        };
    }

    @Test(dataProvider = "validLoginData")
    public void shouldLoginSuccessfullyWithValidCredentials(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        Assert.assertTrue(loginPage.isLoggedIn(),
                "User '" + username + "' should be redirected to the inventory page after login");
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][] {
                { "locked_out_user", "secret_sauce",
                        "Epic sadface: Sorry, this user has been locked out." },
                { "standard_user", "wrong_password",
                        "Epic sadface: Username and password do not match any user in this service" },
                { "invalid_user", "secret_sauce",
                        "Epic sadface: Username and password do not match any user in this service" },
                { "", "secret_sauce", "Epic sadface: Username is required" },
                { "standard_user", "", "Epic sadface: Password is required" }
        };
    }

    @Test(dataProvider = "invalidLoginData")
    public void shouldRejectInvalidLogin(String username, String password, String expectedError) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        Assert.assertEquals(loginPage.getErrorMessage(), expectedError,
                "Error message mismatch for username '" + username + "'");
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory.html"),
                "User should stay on the login page when login is rejected");
    }
}