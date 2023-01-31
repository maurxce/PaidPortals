package me.maurxce.unlockabledimensions.managers;

import me.maurxce.unlockabledimensions.Main;
import me.maurxce.unlockabledimensions.listeners.PortalEnter;
import me.maurxce.unlockabledimensions.listeners.PortalCreate;
import org.bukkit.plugin.PluginManager;

public class EventManager {

    private static final Main main = Main.instance;

    public static void register() {
        PluginManager pm = main.getServer().getPluginManager();

        pm.registerEvents(new PortalEnter(), main);
        pm.registerEvents(new PortalCreate(), main);
    }
}
