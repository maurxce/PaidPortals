package me.maurxce.paidportals.utils;

import org.bukkit.ChatColor;

public class ChatUtils {

    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
