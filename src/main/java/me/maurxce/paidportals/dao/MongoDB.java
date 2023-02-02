package me.maurxce.paidportals.dao;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import me.maurxce.paidportals.managers.FileManager;
import me.maurxce.paidportals.services.Database;
import me.maurxce.paidportals.utils.Logger;
import org.bson.Document;
import org.bukkit.configuration.file.FileConfiguration;

public class MongoDB implements Database {

    private final FileConfiguration config = FileManager.getConfig();
    private final FileConfiguration db = FileManager.getDbMessages();

    private MongoClient client = null;
    private MongoDatabase database = null;
    private MongoCollection<Document> collection = null;

    @Override
    public Database connect() {
        Logger.info(db.getString("connect"));

        MongoClientURI uri = new MongoClientURI(
                String.format("mongodb://%s:%s@%s:%d/%s",
                        Credentials.USERNAME, Credentials.PASSWORD, Credentials.HOST, Credentials.PORT, Credentials.NAME)
        );

        client = new MongoClient(uri);
        database = client.getDatabase(Credentials.NAME);
        collection = database.getCollection("dimensions");

        fillCollection();

        //Logger.info("Connection successful");
        Logger.info(db.getString("connected"));
        return this;
    }

    private void fillCollection() {
        boolean netherEnabled = config.getBoolean("nether.enable");
        boolean endEnabled = config.getBoolean("the_end.enable");

        Document filter = new Document();

        Document query = new Document("paid", 0);
        query.append("nether_locked", netherEnabled);
        query.append("the_end_locked", endEnabled);

        Document update = new Document("$set", query);
        UpdateOptions options = new UpdateOptions().upsert(true);

        collection.updateOne(filter, update, options);
    }

    @Override
    public void disconnect() {
        //Logger.info("Disconnecting database...");
        Logger.info(db.getString("disconnect"));

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

        Document update = new Document("$set", query);
        UpdateOptions options = new UpdateOptions().upsert(true);
        collection.updateOne(filter, update, options);
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

        Document update = new Document("$set", query);
        UpdateOptions options = new UpdateOptions().upsert(true);
        collection.updateOne(filter, update, options);
    }
}
