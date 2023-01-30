package me.maurxce.unlockabledimensions;

import me.maurxce.unlockabledimensions.managers.CommandManager;
import me.maurxce.unlockabledimensions.managers.EconomyManager;
import me.maurxce.unlockabledimensions.managers.FileManager;
import me.maurxce.unlockabledimensions.managers.PlaceholderManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        FileManager.loadFiles();
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
        instance = null;
    }
}
