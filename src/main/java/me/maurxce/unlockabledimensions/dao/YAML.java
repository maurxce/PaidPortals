package me.maurxce.unlockabledimensions.dao;

import me.maurxce.unlockabledimensions.services.Database;

public class YAML implements Database {
    @Override
    public void connect() {

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

    @Override
    public void lockDimension(String dimension) {

    }
}
