package com.mriaps.tests;

import com.mriaps.pages.CartPage;
import com.mriaps.pages.CheckoutPage;
import com.mriaps.pages.InventoryPage;
import com.mriaps.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

public class CheckoutTest extends BaseTest {

    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";
    private static final String BACKPACK = "Sauce Labs Backpack";
    private static final String BIKE_LIGHT = "Sauce Labs Bike Light";
    private static final BigDecimal TAX_RATE = new BigDecimal("0.08");


    private BigDecimal loginAndAddProducts(String... productNames) {
        new LoginPage(driver).login(USERNAME, PASSWORD);

        InventoryPage inventoryPage = new InventoryPage(driver);
        BigDecimal expectedSubtotal = BigDecimal.ZERO;
        for (String productName : productNames) {
            expectedSubtotal = expectedSubtotal.add(inventoryPage.getProductPrice(productName));
            inventoryPage.addProductToCart(productName);
        }
        return expectedSubtotal;
    }

    private void openCheckoutInformationPage() {
        CartPage cartPage = new CartPage(driver);
        cartPage.goToCart();
        cartPage.clickCheckout();
    }

    @Test
    public void shouldShowCorrectProductDetailsInCart() {
        BigDecimal expectedSubtotal = loginAndAddProducts(BACKPACK, BIKE_LIGHT);

        CartPage cartPage = new CartPage(driver);
        cartPage.goToCart();

        Assert.assertEqualsNoOrder(cartPage.getItemNames(), List.of(BACKPACK, BIKE_LIGHT),
                "Cart should contain exactly the products that were added");
        Assert.assertEquals(cartPage.getItemQuantities(), Collections.nCopies(2, 1),
                "Each product should have quantity 1");

        BigDecimal cartPriceSum = cartPage.getItemPrices().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Assert.assertEquals(cartPriceSum, expectedSubtotal,
                "Prices in cart should match prices on the inventory page");
    }

    @Test
    public void shouldCalculateOrderSummaryCorrectly() {
        BigDecimal expectedSubtotal = loginAndAddProducts(BACKPACK, BIKE_LIGHT);
        openCheckoutInformationPage();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillInformation("Rio", "Apriansah", "40123");

        BigDecimal expectedTax = expectedSubtotal.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedTotal = expectedSubtotal.add(expectedTax);

        Assert.assertEquals(checkoutPage.getSubtotal(), expectedSubtotal, "Subtotal mismatch");
        Assert.assertEquals(checkoutPage.getTax(), expectedTax, "Tax mismatch");
        Assert.assertEquals(checkoutPage.getTotal(), expectedTotal, "Total mismatch");
    }

    @Test
    public void shouldCompleteCheckoutSuccessfully() {
        BigDecimal expectedSubtotal = loginAndAddProducts(BACKPACK);
        openCheckoutInformationPage();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillInformation("Rio", "Apriansah", "40123");

        Assert.assertEquals(checkoutPage.getOverviewItemNames(), List.of(BACKPACK),
                "Order overview should list the product that was added");
        Assert.assertEquals(checkoutPage.getSubtotal(), expectedSubtotal,
                "Order overview subtotal should equal the product price");

        checkoutPage.finishOrder();

        Assert.assertEquals(checkoutPage.getCompleteMessage(), "Thank you for your order!");
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete.html"),
                "User should be on the checkout complete page");
    }

    @DataProvider(name = "invalidCheckoutData")
    public Object[][] invalidCheckoutData() {
        return new Object[][] {
                { "", "Apriansah", "40123", "Error: First Name is required" },
                { "Rio", "", "40123", "Error: Last Name is required" },
                { "Rio", "Apriansah", "", "Error: Postal Code is required" }
        };
    }

    @Test(dataProvider = "invalidCheckoutData")
    public void shouldNotProceedWhenRequiredInformationIsMissing(
            String firstName, String lastName, String zipCode, String expectedError) {
        loginAndAddProducts(BACKPACK);
        openCheckoutInformationPage();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillInformation(firstName, lastName, zipCode);

        Assert.assertEquals(checkoutPage.getErrorMessage(), expectedError,
                "Validation message mismatch");
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"),
                "User should stay on the information page when data is incomplete");
    }
}