package me.maurxce.paidportals.dao;

import me.maurxce.paidportals.Main;
import me.maurxce.paidportals.managers.FileManager;
import me.maurxce.paidportals.services.Database;
import me.maurxce.paidportals.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;

public class MySQL implements Database {

    private final FileConfiguration config = FileManager.getConfig();
    private final FileConfiguration db = FileManager.getDbMessages();
    private Connection connection = null;

    @Override
    public Database connect() {
        //Logger.info("Connecting to database...");
        Logger.info(db.getString("connect"));
        String url = String.format("jdbc:mysql://%s:%d/%s", Credentials.HOST, Credentials.PORT, Credentials.NAME);

        try {
            connection = DriverManager.getConnection(url, Credentials.USERNAME, Credentials.PASSWORD);
            //Logger.info("Connection successful");
            Logger.info(db.getString("connected"));
        } catch (SQLException e) {
            //Logger.warning("Error connecting to database");
            Logger.warning(db.getString("error.connect"));
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
                //Logger.warning("Error creating database table");
                Logger.warning(db.getString("error.collection"));
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
                //Logger.warning("Error filling database table");
                Logger.warning(db.getString("error.fill"));
                e.printStackTrace();
            }
        });
    }

    @Override
    public void disconnect() {
        //Logger.warning("Disconnecting database...");
        Logger.info(db.getString("disconnect"));

        try {
            if (connection != null && !connection.isClosed()) connection.close();
            connection = null;
        } catch (SQLException e) {
            //Logger.warning("Error disconnecting database");
            Logger.warning(db.getString("error.disconnect"));
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
            //Logger.warning("Error getting value from database table");
            Logger.warning(db.getString("error.get"));
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
            //Logger.warning("Error updating value in database table");
            Logger.warning(db.getString("error.post"));
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
            //Logger.warning("Error getting value from database table");
            Logger.warning(db.getString("error.get"));
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
            //Logger.warning("Error updating value in database table");
            Logger.warning(db.getString("error.post"));
            e.printStackTrace();
        }
    }
}
