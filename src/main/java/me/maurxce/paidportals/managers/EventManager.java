package me.maurxce.paidportals.managers;

import me.maurxce.paidportals.Main;
import me.maurxce.paidportals.listeners.PortalEnter;
import me.maurxce.paidportals.listeners.PortalCreate;
import org.bukkit.plugin.PluginManager;

public class EventManager {

    private static final Main main = Main.instance;

    public static void register() {
        PluginManager pm = main.getServer().getPluginManager();

        pm.registerEvents(new PortalEnter(), main);
        pm.registerEvents(new PortalCreate(), main);
    }
}
