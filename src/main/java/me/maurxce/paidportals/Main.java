package me.maurxce.paidportals;

import me.maurxce.paidportals.managers.*;
import me.maurxce.paidportals.services.Database;
import me.maurxce.paidportals.utils.Logger;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;
    private DatabaseManager dbManager = null;

    @Override
    public void onEnable() {
        instance = this;
        dbManager = new DatabaseManager();

        FileManager.loadFiles();
        dbManager.setupDatabase();

        CommandManager.register();
        EventManager.register();

        checkDependencies();

        // bStats
        /**
         * @TODO move somewhere else
         */
        int pluginId = 17603;
        Metrics metrics = new Metrics(this, pluginId);
    }

    private void checkDependencies() {
        if (!EconomyManager.setupEconomy()) {
            Logger.error("Couldn't find dependency: Vault");
            disablePlugin();
            return;
        }

        PlaceholderManager placeholderManager = new PlaceholderManager();
        if (!placeholderManager.setupPlaceholders()) {
            Logger.error("Couldn't find dependency: PlaceholderAPI");
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
