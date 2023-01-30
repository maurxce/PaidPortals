package me.maurxce.unlockabledimensions;

import me.maurxce.unlockabledimensions.managers.*;
import me.maurxce.unlockabledimensions.services.Database;
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

        checkDependencies();
    }

    private void checkDependencies() {
        if (!EconomyManager.setupEconomy()) {
            getLogger().severe("Couldn't find dependency: Vault");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!PlaceholderManager.setupPlaceholders()) {
            getLogger().warning("Couldn't find dependency: PlaceholderAPI");
            return;
        }
    }

    @Override
    public void onDisable() {
        dbManager.closeDatabase();

        instance = null;
    }

    public DatabaseManager getDbManager() {
        return dbManager;
    }
}
