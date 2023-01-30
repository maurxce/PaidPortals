package me.maurxce.unlockabledimensions.listeners;

import me.maurxce.unlockabledimensions.managers.FileManager;
import me.maurxce.unlockabledimensions.utils.ChatUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalCreate implements Listener {

    private final FileConfiguration config = FileManager.getConfig();
    private final FileConfiguration lang = FileManager.getLang();

    @EventHandler
    public void onPortalCreate(PortalCreateEvent e) {
        Player player = (Player) e.getEntity();

        if (!isAllowed(player)) {
            String portalCreation = lang.getString("portal-creation");

            player.sendMessage(ChatUtils.translate(portalCreation));
            e.setCancelled(true);
        }
    }

    private boolean isAllowed(Player player) {
        boolean allowCreate = config.getBoolean("player-portal-create");
        boolean hasPermission = player.hasPermission("dimensions.ignore");

        return allowCreate || hasPermission || player.isOp();
    }
}
