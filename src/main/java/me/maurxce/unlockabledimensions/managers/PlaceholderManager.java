package me.maurxce.unlockabledimensions.managers;

import org.bukkit.Bukkit;

public class PlaceholderManager {

    public static boolean setupPlaceholders() {
        if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) return false;

        // regitser placeholders here
        return true;
    }
}
