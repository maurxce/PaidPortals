package me.maurxce.unlockabledimensions.utils;

import org.bukkit.ChatColor;

public class ChatUtils {

    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
