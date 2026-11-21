package com.mriaps.pages;

import com.mriaps.utils.PriceUtils;
import com.mriaps.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.util.List;

public class CartPage {

    private final WebDriver driver;
    private final WaitHelper wait;

    private final By checkoutButton = By.id("checkout");
    private final By cartLink = By.className("shopping_cart_link");
    private final By cartItems = By.className("cart_item");
    private final By itemName = By.className("inventory_item_name");
    private final By itemPrice = By.className("inventory_item_price");
    private final By itemQuantity = By.className("cart_quantity");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    public void goToCart() {
        driver.findElement(cartLink).click();
        wait.visible(checkoutButton);
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