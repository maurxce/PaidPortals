package me.maurxce.unlockabledimensions.dao;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import me.maurxce.unlockabledimensions.managers.FileManager;
import me.maurxce.unlockabledimensions.services.Database;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class MongoDB implements Database {

    private final FileConfiguration config = FileManager.getConfig();
    private MongoClient client = null;
    private MongoDatabase database = null;
    private MongoCollection<Document> collection = null;

    @Override
    public Database connect() {
        Bukkit.getLogger().info("Connecting to database...");

        MongoClientURI uri = new MongoClientURI(
                String.format("mongodb://%s:%s@%s:%d",
                        Credentials.USERNAME, Credentials.PASSWORD, Credentials.HOST, Credentials.PORT)
        );

        client = new MongoClient(uri);
        database = client.getDatabase(Credentials.NAME);
        collection = database.getCollection("dimensions");

        fillCollection();

        Bukkit.getLogger().info("Connection successful");
        return this;
    }

    private void fillCollection() {
        boolean netherEnabled = config.getBoolean("nether.enable");
        boolean endEnabled = config.getBoolean("the_end.enable");

        Document query = new Document();

        Document update = new Document("paid", 0);
        update.append("nether_locked", netherEnabled);
        update.append("the_end_locked", endEnabled);

        UpdateOptions options = new UpdateOptions().upsert(true);
        collection.updateOne(query, update, options);
    }

    @Override
    public void disconnect() {
        Bukkit.getLogger().info("Disconnecting database...");

        client.close();
        client = null;
    }

    @Override
    public int getPaid() {
        FindIterable<Document> result = collection.find();
        for (Document doc : result) {
            if (doc.containsKey("paid")) return doc.getInteger("paid");
        }

        return -1;
    }

    @Override
    public void setPaid(int amount) {
        Document filter = new Document("paid", getPaid());
        Document query = new Document("paid", amount);
        collection.updateOne(filter, query);
    }

    @Override
    public void addPaid(int amount) {
        setPaid(getPaid() + amount);
    }

    @Override
    public boolean isLocked(String dimension) {
        FindIterable<Document> result = collection.find();
        for (Document doc : result) {
            if (doc.containsKey(dimension + "_locked")) return doc.getBoolean(dimension + "_locked");
        }

        return true;
    }

    @Override
    public void unlockDimension(String dimension) {
        Document filter = new Document(dimension + "_locked", isLocked(dimension));
        Document query = new Document(dimension + "_locked", false);
        collection.updateOne(filter, query);
    }
}
