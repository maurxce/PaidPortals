package me.maurxce.unlockabledimensions.services;

import me.maurxce.unlockabledimensions.managers.FileManager;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;

public interface Database {

    interface Credentials {
        String TYPE = FileManager.getConfig().getString("database.type");
        String HOST = FileManager.getConfig().getString("database.host");
        String PORT = FileManager.getConfig().getString("database.port");
        String NAME = FileManager.getConfig().getString("database.name");
        String USERNAME = FileManager.getConfig().getString("database.username");
        String PASSWORD = FileManager.getConfig().getString("database.password");
    }

    void connect() throws IOException, InvalidConfigurationException;
    void disconnect() throws IOException;

    Database getInstance();

    int getPaid();
    void setPaid(int amount);
    void addPaid(int amount);

    boolean isLocked(String dimension);
    void unlockDimension(String dimension);
    void lockDimension(String dimension);
}
