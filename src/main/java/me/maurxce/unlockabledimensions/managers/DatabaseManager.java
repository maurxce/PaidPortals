package me.maurxce.unlockabledimensions.managers;

import me.maurxce.unlockabledimensions.dao.MongoDB;
import me.maurxce.unlockabledimensions.dao.MySQL;
import me.maurxce.unlockabledimensions.dao.YAML;
import me.maurxce.unlockabledimensions.services.Database;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @TODO: add sqlite, nitrite
 */
public class DatabaseManager {

    private static final FileConfiguration config = FileManager.getConfig();
    private Database database = null;

    public void setupDatabase() {
        String type = config.getString("database.type");

        switch (type.toUpperCase()) {
            case "YAML":
                database = new YAML().connect();
                break;
            case "MYSQL":
                database = new MySQL().connect();
                break;
            case "MONGODB":
                database = new MongoDB().connect();
                break;
            default:
                Bukkit.getLogger().severe("Invalid database type: " + type);
                break;
        }
    }

    public void closeDatabase() {
        if (database != null) database.disconnect();
    }

    public Database getDatabase() {
        return database;
    }
}
