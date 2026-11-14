package com.mriaps.tests;

import com.mriaps.pages.CartPage;
import com.mriaps.pages.CheckoutPage;
import com.mriaps.pages.InventoryPage;
import com.mriaps.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    @Test
    public void shouldCompleteCheckoutSuccessfully() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addProductToCart("Sauce Labs Backpack");

        CartPage cartPage = new CartPage(driver);
        cartPage.goToCart();
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillInformation("Rio", "Apriansah", "40123");
        checkoutPage.finishOrder();

        Assert.assertEquals(checkoutPage.getCompleteMessage(), "Thank you for your order!");
    }
}