package me.maurxce.paidportals.managers;

import me.maurxce.paidportals.Main;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.FileConfiguration;

public class MetricsManager {

    private static final FileConfiguration config = FileManager.getConfig();

    public static void enable() {
        boolean allowMetrics = config.getBoolean("metrics");
        if (!allowMetrics) return;

        int id = 17603;
        Metrics metrics = new Metrics(Main.instance, id);
    }
}
