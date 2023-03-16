package me.maurxce.paidportals.managers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.maurxce.paidportals.Main;
import me.maurxce.paidportals.services.Database;
import me.maurxce.paidportals.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class PlaceholderManager extends PlaceholderExpansion {

    private final FileConfiguration lang = FileManager.getLang();
    private final Database database = Main.instance.getDatabase();

    @Override
    public @NotNull String getIdentifier() {
        return "paidportals";
    }

    @Override
    public @NotNull String getAuthor() {
        return "maurxce";
    }

    @Override
    public @NotNull String getVersion() {
        return Main.instance.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        boolean nether = params.equalsIgnoreCase("nether");
        boolean end = params.equalsIgnoreCase("the_end");

        if (nether || end) {
            String dimension = params.toLowerCase();

            if (database.isLocked(dimension)) {
                String locked = lang.getString("placeholder-dimension-locked");
                return ChatUtils.translate(locked);
            } else {
                String unlocked = lang.getString("placeholder-dimension-unlocked");
                return ChatUtils.translate(unlocked);
            }
        }

        return null;
    }
}
