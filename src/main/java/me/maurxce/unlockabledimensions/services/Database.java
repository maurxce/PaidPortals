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

    Database connect();

    void disconnect();

    /**
     * @return total amount in pool
     */
    int getPaid();

    /**
     * @param amount
     */
    void setPaid(int amount);

    /**
     * @param amount
     */
    void addPaid(int amount);

    /**
     * @param dimension
     * @return whether dimension is locked or not
     */
    boolean isLocked(String dimension);

    /**
     * @param dimension
     */
    void unlockDimension(String dimension);

    /**
     * @TODO: remove if not needed
     * @param dimension
     */
    void lockDimension(String dimension);
}
