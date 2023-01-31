package me.maurxce.unlockabledimensions.dao;

import me.maurxce.unlockabledimensions.Main;
import me.maurxce.unlockabledimensions.managers.FileManager;
import me.maurxce.unlockabledimensions.services.Database;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;

public class MySQL implements Database {

    private final FileConfiguration config = FileManager.getConfig();
    private Connection connection = null;

    @Override
    public Database connect() {
        String url = String.format("jdbc:mysql://%s:%d/%s", Credentials.HOST, Credentials.PORT, Credentials.NAME);

        try {
            connection = DriverManager.getConnection(url, Credentials.USERNAME, Credentials.PASSWORD);
            Bukkit.getLogger().info("Connection successful");
        } catch (SQLException e) {
            Bukkit.getLogger().warning("Error connecting to database");
            e.printStackTrace();

            Main.instance.disablePlugin();
        }

        createTable();
        fillTable();

        return this;
    }

    private void createTable() {
        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
            String query = "CREATE TABLE IF NOT EXISTS dimensions (" +
                               "paid INT DEFAULT 0," +
                               "nether_locked BOOL DEFAULT TRUE," +
                               "the_end_locked BOOL DEFAULT TRUE" +
                           ");";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate();
            } catch (SQLException e) {
                Bukkit.getLogger().warning("Error creating table");
                e.printStackTrace();
            }
        });
    }

    private void fillTable() {
        boolean netherEnabled = config.getBoolean("nether.enable");
        boolean endEnabled = config.getBoolean("the_end.enable");

        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
            String query = "INSERT INTO dimensions " +
                           "SELECT ?, ?, ? FROM DUAL " +
                           "WHERE NOT EXISTS (SELECT * FROM dimensions);";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, 0);
                statement.setBoolean(2, netherEnabled);
                statement.setBoolean(3, endEnabled);

                statement.executeUpdate();
            } catch (SQLException e) {
                Bukkit.getLogger().warning("Error filling table");
                e.printStackTrace();
            }
        });
    }

    @Override
    public void disconnect() {
        Bukkit.getLogger().info("Disconnecting database...");

        try {
            if (connection != null && !connection.isClosed()) connection.close();
            connection = null;
        } catch (SQLException e) {
            Bukkit.getLogger().warning("Error disconnecting database");
            e.printStackTrace();
        }
    }

    @Override
    public int getPaid() {
        String query = "SELECT paid FROM dimensions;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) return resultSet.getInt("paid");
        } catch (SQLException e) {
            Bukkit.getLogger().warning("Error getting value from table");
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public void setPaid(int amount) {
        String query = "UPDATE dimensions SET paid = ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, amount);
            statement.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().warning("Error updating value in table");
            e.printStackTrace();
        }
    }

    @Override
    public void addPaid(int amount) {
        setPaid(getPaid() + amount);
    }

    @Override
    public boolean isLocked(String dimension) {
        String locked = dimension + "_locked";
        String query = "SELECT " + locked + " FROM dimensions;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) return resultSet.getBoolean(dimension + "_locked");
        } catch (SQLException e) {
            Bukkit.getLogger().warning("Error getting value from table");
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void unlockDimension(String dimension) {
        String locked = dimension + "_locked";
        String query = "UPDATE dimensions SET " + locked + " = FALSE;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().warning("Error updating value in table");
            e.printStackTrace();
        }
    }
}