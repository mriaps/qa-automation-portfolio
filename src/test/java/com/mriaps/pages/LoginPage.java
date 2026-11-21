package com.mriaps.pages;

import com.mriaps.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final WebDriver driver;
    private final WaitHelper wait;

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public String getErrorMessage() {
        return wait.visible(errorMessage).getText();
    }

    // Menunggu sampai URL berpindah ke halaman inventory. Mengembalikan false jika tidak berpindah sebelum timeout.
    public boolean isLoggedIn() {
        try {
            return wait.urlContains("inventory.html");
        } catch (TimeoutException e) {
            return false;
        }
    }
}