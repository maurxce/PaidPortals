package me.maurxce.paidportals.listeners;

import me.maurxce.paidportals.Main;
import me.maurxce.paidportals.managers.FileManager;
import me.maurxce.paidportals.services.Database;
import me.maurxce.paidportals.utils.ChatUtils;
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
        boolean hasPermission = player.hasPermission("paidportals.ignore");

        return allowedEnter || !database.isLocked(dimension) || hasPermission || player.isOp();
    }
}
