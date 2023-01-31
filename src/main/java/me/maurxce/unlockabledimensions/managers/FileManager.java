package me.maurxce.unlockabledimensions.managers;

import me.maurxce.unlockabledimensions.Main;
import me.maurxce.unlockabledimensions.utils.RandomString;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private static final FileConfiguration config = new YamlConfiguration();
    private static final FileConfiguration lang = new YamlConfiguration();

    private static final File configFile = new File(getDataFolder(), "config.yml");
    private static final File langFile = new File(getDataFolder(), "lang.yml");

    public static File getDataFolder() {
        return Main.instance.getDataFolder();
    }

    public static void loadFiles() {
        if (!getDataFolder().exists()) getDataFolder().mkdirs();
        if (!configFile.exists()) saveResource("config.yml");
        if (!langFile.exists()) saveResource("lang.yml");

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
        }

        config.load(configFile);
        lang.load(langFile);

        generatePassword();
    }

    private static void generatePassword() throws IOException, InvalidConfigurationException {
        if (config.get("database.password") == null) {
            config.set("database.password", RandomString.generate());
            reloadFiles(true);
        }
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static FileConfiguration getLang() {
        return lang;
    }
}
