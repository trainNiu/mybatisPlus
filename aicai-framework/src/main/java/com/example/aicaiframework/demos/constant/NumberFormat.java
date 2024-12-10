package com.example.aicaiframework.demos.constant;


import java.util.Locale;

/**
 * 数字 format
 */
public class NumberFormat {

    public final static String INT = "0";
    public final static String INT_0 = "###,##0";

    public final static String NUMBER = "0.00";
    public final static String NUMBER_2 = "###,##0.00";

    public static String formatPercent(Object object,int num) {
        java.text.NumberFormat format = java.text.NumberFormat.getPercentInstance(Locale.getDefault());
        format.setMinimumFractionDigits(num);
        format.setMaximumFractionDigits(num);
        return format.format(object);
    }

    public static String format(Object number,int scale) {
        java.text.NumberFormat format = java.text.NumberFormat.getNumberInstance(Locale.getDefault());
        format.setMinimumFractionDigits(scale);
        format.setMaximumFractionDigits(scale);
        return format.format(number);
    }
}
