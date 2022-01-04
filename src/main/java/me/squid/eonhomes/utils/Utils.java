package me.squid.eonhomes.utils;

import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Utils {

    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static double round(double num, int places) {
        BigDecimal bd = new BigDecimal(num, MathContext.DECIMAL32);
        bd = bd.setScale(places, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }
}
