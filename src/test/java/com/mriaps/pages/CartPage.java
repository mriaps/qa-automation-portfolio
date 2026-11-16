package com.mriaps.pages;

import com.mriaps.utils.PriceUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By checkoutButton = By.id("checkout");
    private final By cartLink = By.className("shopping_cart_link");
    private final By cartItems = By.className("cart_item");
    private final By itemName = By.className("inventory_item_name");
    private final By itemPrice = By.className("inventory_item_price");
    private final By itemQuantity = By.className("cart_quantity");

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

    public List<String> getItemNames() {
        return driver.findElements(cartItems).stream()
                .map(item -> item.findElement(itemName).getText())
                .toList();
    }

    public List<BigDecimal> getItemPrices() {
        return driver.findElements(cartItems).stream()
                .map(item -> PriceUtils.parse(item.findElement(itemPrice).getText()))
                .toList();
    }

    public List<Integer> getItemQuantities() {
        return driver.findElements(cartItems).stream()
                .map(item -> Integer.parseInt(item.findElement(itemQuantity).getText()))
                .toList();
    }
}