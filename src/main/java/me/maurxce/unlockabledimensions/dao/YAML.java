package me.maurxce.unlockabledimensions.dao;

import me.maurxce.unlockabledimensions.managers.FileManager;
import me.maurxce.unlockabledimensions.services.Database;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YAML implements Database {

    private final FileConfiguration database = new YamlConfiguration();
    private final File file = new File(FileManager.getDataFolder(), "data/" + Credentials.NAME +".yml");

    @Override
    public void connect() {
        if (!file.exists()) {
            FileManager.saveResource("data/" + Credentials.NAME + ".yml");
            reload(false);

            database.set("paid", 0);
            database.set("nether-locked", true);
            database.set("the_end-locked", true);
            reload(true);

            return;
        }

        reload(false);
    }

    @Override
    public void disconnect() throws IOException {
        database.save(file);
        database = null;
    }

    @Override
    public Database getInstance() {
        return (Database) database;
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

    @Override
    public void lockDimension(String dimension) {
        database.set(dimension + "-locked", true);
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
