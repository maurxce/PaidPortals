package me.maurxce.unlockabledimensions.dao;

import me.maurxce.unlockabledimensions.services.Database;

/**
 * @TODO: actually make this functional
 */
public class SQLite implements Database {
    @Override
    public Database connect() {
        return null;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public int getPaid() {
        return 0;
    }

    @Override
    public void setPaid(int amount) {

    }

    @Override
    public void addPaid(int amount) {

    }

    @Override
    public boolean isLocked(String dimension) {
        return false;
    }

    @Override
    public void unlockDimension(String dimension) {

    }
}
