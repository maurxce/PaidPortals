package me.maurxce.unlockabledimensions.managers;

import me.maurxce.unlockabledimensions.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private static final FileConfiguration config = new YamlConfiguration();
    private static final FileConfiguration lang = new YamlConfiguration();
    private static final FileConfiguration pool = new YamlConfiguration();

    private static final File configFile = new File(getDataFolder(), "config.yml");
    private static final File langFile = new File(getDataFolder(), "lang.yml");
    private static final File poolFile = new File(getDataFolder(), "data/pool.yml");

    public static File getDataFolder() {
        return Main.instance.getDataFolder();
    }

    public static void loadFiles() {
        if (!getDataFolder().exists()) getDataFolder().mkdirs();
        if (!configFile.exists()) saveResource("config.yml");
        if (!langFile.exists()) saveResource("lang.yml");
        if (!poolFile.exists()) saveResource("data/pool.yml");

        try {
            reloadFiles(false);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void saveResource(String resource) {
        Main.instance.saveResource(resource, false);
    }

    public static void reloadFiles(boolean save) throws IOException, InvalidConfigurationException {
        if (save) {
            config.save(configFile);
            lang.save(langFile);
            pool.save(poolFile);
        }

        config.load(configFile);
        lang.load(langFile);
        pool.load(poolFile);
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static FileConfiguration getLang() {
        return lang;
    }

    public static FileConfiguration getPool() {
        return pool;
    }
}
