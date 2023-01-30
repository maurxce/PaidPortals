package me.maurxce.unlockabledimensions.managers;

public class PlaceholderManager {

    public static boolean setupPlaceholders() {
        if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) return false;

        // regitser placeholders here
        return true;
    }
}
