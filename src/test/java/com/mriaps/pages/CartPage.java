package com.mriaps.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    private final WebDriver driver;

    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }

    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }
}