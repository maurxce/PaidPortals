package me.maurxce.paidportals;

import me.maurxce.paidportals.managers.*;
import me.maurxce.paidportals.services.Database;
import me.maurxce.paidportals.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
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

       checkDependencies();

        CommandManager.register();
        EventManager.register();

        MetricsManager.enable();
    }

    private void checkDependencies() {
        PluginManager pm = Bukkit.getPluginManager();

        if (pm.getPlugin("PlaceholderAPI") == null) {
            Logger.error("Couldn't find PlaceholderAPI!");
            pm.disablePlugin(this);
        }

        new PlaceholderManager().register();

        if (pm.getPlugin("Vault") == null) {
            Logger.error("Couldn't find Vault!");
            pm.disablePlugin(this);
        }

        boolean setupEconomy = EconomyManager.setupEconomy();
        if (!setupEconomy) {
            Logger.error("Couldn't set up Vault economy!");
            pm.disablePlugin(this);
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
