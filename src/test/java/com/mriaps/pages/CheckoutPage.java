package com.mriaps.pages;

import com.mriaps.utils.PriceUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Step 1: informasi pembeli
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By zipCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    // Step 2: ringkasan pesanan
    private final By overviewItems = By.className("cart_item");
    private final By itemName = By.className("inventory_item_name");
    private final By subtotalLabel = By.className("summary_subtotal_label");
    private final By taxLabel = By.className("summary_tax_label");
    private final By totalLabel = By.className("summary_total_label");
    private final By finishButton = By.id("finish");

    // Halaman selesai
    private final By completeHeader = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillInformation(String firstName, String lastName, String zipCode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(zipCodeField).sendKeys(zipCode);
        driver.findElement(continueButton).click();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    public List<String> getOverviewItemNames() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(subtotalLabel));
        return driver.findElements(overviewItems).stream()
                .map(item -> item.findElement(itemName).getText())
                .toList();
    }

    public BigDecimal getSubtotal() {
        return amountOf(subtotalLabel);
    }

    public BigDecimal getTax() {
        return amountOf(taxLabel);
    }

    public BigDecimal getTotal() {
        return amountOf(totalLabel);
    }

    private BigDecimal amountOf(By locator) {
        return PriceUtils.parse(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText());
    }

    public void finishOrder() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(finishButton));
        driver.findElement(finishButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader));
    }

    public String getCompleteMessage() {
        return driver.findElement(completeHeader).getText();
    }
}