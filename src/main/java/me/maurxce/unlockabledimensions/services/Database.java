package me.maurxce.unlockabledimensions.services;

public interface Database {

    void connect();
    void disconnect();

    int getPaid();
    void setPaid(int amount);
    void addPaid(int amount);

    boolean isLocked(String dimension);
    void unlockDimension(String dimension);
    void lockDimension(String dimension);
}
