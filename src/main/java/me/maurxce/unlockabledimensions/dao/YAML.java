package me.maurxce.unlockabledimensions.dao;

import me.maurxce.unlockabledimensions.managers.FileManager;
import me.maurxce.unlockabledimensions.services.Database;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YAML implements Database {

    private FileConfiguration database = null;
    private File file = null;

    @Override
    public Database connect() {
        database = new YamlConfiguration();
        file = new File(FileManager.getDataFolder(), "data/" + Credentials.NAME +".yml");

        if (!file.exists()) FileManager.saveResource("data/" + Credentials.NAME + ".yml");

        reload(false);
        return this;
    }

    @Override
    public void disconnect() {
        try {
            database.save(file);
            database = null;
        } catch (IOException e) {
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
        database.set("paid", getPaid() + amount);
        reload(true);
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
        }
    }
}
