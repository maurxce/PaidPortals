package me.maurxce.unlockabledimensions.listeners;

import me.maurxce.unlockabledimensions.Main;
import me.maurxce.unlockabledimensions.managers.FileManager;
import me.maurxce.unlockabledimensions.services.Database;
import me.maurxce.unlockabledimensions.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalEnter implements Listener {

    private final FileConfiguration config = FileManager.getConfig();
    private final FileConfiguration lang = FileManager.getLang();

    private final Database database = Main.instance.getDatabase();

    String worldName = Bukkit.getServer().getWorlds().get(0).getName();

    @EventHandler
    public void onPortalEnter(PlayerPortalEvent e) {
        Player player = e.getPlayer();
        String dimension = e.getTo().getWorld().getName();

        boolean isOverworld = dimension.equals(worldName);
        if (!isOverworld && !isAllowed(player, dimension)) {
            String portalEnter = lang.getString("portal-enter");

            player.sendMessage(ChatUtils.translate(portalEnter));
            e.setCancelled(true);
        }
    }

    private boolean isAllowed(Player player, String dimension) {
        dimension = dimension.replace(worldName + "_", "");

        boolean allowedEnter = config.getBoolean("player-portal-enter");
        boolean hasPermission = player.hasPermission("dimensions.ignore");

        return allowedEnter || !database.isLocked(dimension) || hasPermission || player.isOp();
    }
}
