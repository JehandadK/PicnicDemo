package com.jehandadk.picnic.helpers;

import java.text.NumberFormat;

/**
 * Created by jehandad.kamal on 1/24/2016.
 */
public class Utils {

    /**
     * Formats using current Locale
     *
     * @param amount a valid double should be provided
     */
    public static String formatCurrency(String amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        return currencyFormatter.format(Double.parseDouble(amount));
    }

    /**
     * Formats using current Locale
     *
     * @param amount a valid double
     */
    public static String formatCurrency(double amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        return currencyFormatter.format(amount);
    }
}
