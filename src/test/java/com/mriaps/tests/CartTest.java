package com.mriaps.tests;

import com.mriaps.pages.InventoryPage;
import com.mriaps.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    @Test
    public void shouldAddProductToCart() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addProductToCart("Sauce Labs Backpack");

        Assert.assertEquals(inventoryPage.getCartItemCount(), 1,
                "Cart should contain 1 item after adding a product");
    }
}