package com.mriaps.tests;

import com.mriaps.config.ConfigReader;
import com.mriaps.pages.InventoryPage;
import com.mriaps.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    @Test
    public void shouldAddProductToCart() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.standardUsername(), ConfigReader.standardPassword());

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addProductToCart("Sauce Labs Backpack");

        Assert.assertEquals(inventoryPage.getCartItemCount(), 2,
                "Cart should contain 1 item after adding a product");
    }
}