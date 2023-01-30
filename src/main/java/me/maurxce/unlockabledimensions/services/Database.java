package me.maurxce.unlockabledimensions.services;

import me.maurxce.unlockabledimensions.managers.FileManager;

public interface Database {

    interface Credentials {
        String TYPE = FileManager.getConfig().getString("database.type");
        String HOST = FileManager.getConfig().getString("database.host");
        int PORT = FileManager.getConfig().getInt("database.port");
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
     * @param amount set the amount of total money in the dimensions pool
     */
    void setPaid(int amount);

    /**
     * @param amount add a certain amount to the dimensions pool
     */
    void addPaid(int amount);

    /**
     * @param dimension the dimension
     * @return whether dimension is locked or not
     */
    boolean isLocked(String dimension);

    /**
     * @param dimension the dimension
     */
    void unlockDimension(String dimension);
}
