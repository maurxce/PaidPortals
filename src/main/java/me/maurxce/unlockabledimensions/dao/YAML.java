package me.maurxce.unlockabledimensions.dao;

import me.maurxce.unlockabledimensions.Main;
import me.maurxce.unlockabledimensions.managers.FileManager;
import me.maurxce.unlockabledimensions.services.Database;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YAML implements Database {

    private final FileConfiguration config = FileManager.getConfig();
    private FileConfiguration database = null;
    private File file = null;

    @Override
    public Database connect() {
        Bukkit.getLogger().info("Connecting to database...");

        database = new YamlConfiguration();
        file = new File(FileManager.getDataFolder(), "data/unlockabledimensions.yml");

        createFile();
        fillFile();

        Bukkit.getLogger().info("Connection successful");
        return this;
    }

    private void createFile() {
        if (!file.exists()) FileManager.saveResource("data/unlockabledimensions.yml");
        reload(false);
    }

    private void fillFile() {
        boolean netherEnabled = config.getBoolean("nether.enable");
        boolean endEnabled = config.getBoolean("the_end.enable");

        database.set("nether-locked", netherEnabled);
        database.set("the_end-locked", endEnabled);

        reload(true);
    }

    @Override
    public void disconnect() {
        Bukkit.getLogger().info("Disconnecting database...");

        try {
            database.save(file);
            database = null;
        } catch (IOException e) {
            Bukkit.getLogger().warning("Error disconnecting database");
            e.printStackTrace();
        }
    }

    @Override
    public int getPaid() {
        return database.getInt("paid");
    }

    @Override
    public void setPaid(int amount) {
        database.set("paid", amount);
        reload(true);
    }

    @Override
    public void addPaid(int amount) {
        setPaid(getPaid() + amount);
    }

    @Override
    public boolean isLocked(String dimension) {
        return database.getBoolean(dimension + "-locked");
    }

    @Override
    public void unlockDimension(String dimension) {
        database.set(dimension + "-locked", false);
        reload(true);
    }

    private void reload(boolean save) {
        try {
            if (save) database.save(file);
            database.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            Main.instance.disablePlugin();
        }
    }
}
