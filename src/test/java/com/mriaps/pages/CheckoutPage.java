package com.mriaps.pages;

import com.mriaps.utils.PriceUtils;
import com.mriaps.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutPage {

    private final WebDriver driver;
    private final WaitHelper wait;

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
        this.wait = new WaitHelper(driver);
    }

    public void fillInformation(String firstName, String lastName, String zipCode) {
        wait.visible(firstNameField);
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(zipCodeField).sendKeys(zipCode);
        driver.findElement(continueButton).click();
    }

    public String getErrorMessage() {
        return wait.visible(errorMessage).getText();
    }

    public List<String> getOverviewItemNames() {
        wait.visible(subtotalLabel);
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
        return PriceUtils.parse(wait.visible(locator).getText());
    }

    public void finishOrder() {
        wait.visible(finishButton);
        driver.findElement(finishButton).click();
        wait.visible(completeHeader);
    }

    public String getCompleteMessage() {
        return driver.findElement(completeHeader).getText();
    }
}