package com.mriaps.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By checkoutButton = By.id("checkout");
    private final By cartLink = By.className("shopping_cart_link");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToCart() {
        driver.findElement(cartLink).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutButton));
    }

    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }
}