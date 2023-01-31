package me.maurxce.unlockabledimensions.utils;

import me.maurxce.unlockabledimensions.Main;
import org.bukkit.Bukkit;

public class Logger {

    static String pluginPrefix = Main.instance.getDescription().getPrefix();
    static String prefix = "[" + pluginPrefix + "] ";

    public static void info(String text) {
        Bukkit.getLogger().info(prefix + text);
    }

    public static void warning(String text) {
        Bukkit.getLogger().warning(prefix + text);
    }

    public static void error(String text) {
        Bukkit.getLogger().severe(prefix + text);
    }
}
