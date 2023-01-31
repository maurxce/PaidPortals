package me.maurxce.unlockabledimensions;

import me.maurxce.unlockabledimensions.managers.*;
import me.maurxce.unlockabledimensions.services.Database;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;
    DatabaseManager dbManager = null;

    @Override
    public void onEnable() {
        instance = this;
        dbManager = new DatabaseManager();

        FileManager.loadFiles();
        dbManager.setupDatabase();

        CommandManager.register();
        EventManager.register();

        checkDependencies();
    }

    private void checkDependencies() {
        if (!EconomyManager.setupEconomy()) {
            getLogger().severe("Couldn't find dependency: Vault");
            disablePlugin();
            return;
        }

        PlaceholderManager placeholderManager = new PlaceholderManager();
        if (!placeholderManager.setupPlaceholders()) {
            getLogger().warning("Couldn't find dependency: PlaceholderAPI");
            return;
        }
    }

    @Override
    public void onDisable() {
        dbManager.closeDatabase();

        instance = null;
    }

    public Database getDatabase() {
        return dbManager.getDatabase();
    }
    public void disablePlugin() {
        Bukkit.getServer().getPluginManager().disablePlugin(this);
    }
}
