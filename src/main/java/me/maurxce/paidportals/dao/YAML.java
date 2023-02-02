package me.maurxce.paidportals.dao;

import me.maurxce.paidportals.Main;
import me.maurxce.paidportals.managers.FileManager;
import me.maurxce.paidportals.services.Database;
import me.maurxce.paidportals.utils.Logger;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YAML implements Database {

    private final FileConfiguration config = FileManager.getConfig();
    private final FileConfiguration db = FileManager.getDbMessages();
    private FileConfiguration database = null;
    private File file = null;

    @Override
    public Database connect() {
        //Logger.info("Connecting to database...");
        Logger.info(db.getString("connect"));

        database = new YamlConfiguration();
        file = new File(FileManager.getDataFolder(), "data/unlockabledimensions.yml");

        createFile();
        fillFile();

        //Logger.info("Connection successful");
        Logger.info(db.getString("connected"));
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
        //Logger.info("Disconnecting database");
        Logger.info(db.getString("disconnect"));

        try {
            database.save(file);
            database = null;
        } catch (IOException e) {
            //Logger.warning("Error disconnecting database");
            Logger.warning(db.getString("error-disconnect"));
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
