package com.mriaps.utils;

import java.math.BigDecimal;

public final class PriceUtils {

    private PriceUtils() {
    }

    // Mengubah teks seperti "Item total: $29.99" atau "$9.99" menjadi BigDecimal
    public static BigDecimal parse(String text) {
        return new BigDecimal(text.replaceAll("[^0-9.]", ""));
    }
}