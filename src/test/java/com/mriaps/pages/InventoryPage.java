package com.mriaps.pages;

import com.mriaps.utils.PriceUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import java.math.BigDecimal;
import java.util.List;

public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartBadge = By.className("shopping_cart_badge");
    private final By inventoryItems = By.className("inventory_item");
    private final By itemName = By.className("inventory_item_name");
    private final By itemPrice = By.className("inventory_item_price");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private WebElement findProduct(String productName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryItems));
        for (WebElement item : driver.findElements(inventoryItems)) {
            if (item.findElement(itemName).getText().equals(productName)) {
                return item;
            }
        }
        throw new NoSuchElementException("Product not found: " + productName);
    }

    public void addProductToCart(String productName) {
        findProduct(productName).findElement(By.tagName("button")).click();
    }

    public BigDecimal getProductPrice(String productName) {
        return PriceUtils.parse(findProduct(productName).findElement(itemPrice).getText());
    }

    public int getCartItemCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        if (badges.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(badges.get(0).getText());
    }
}