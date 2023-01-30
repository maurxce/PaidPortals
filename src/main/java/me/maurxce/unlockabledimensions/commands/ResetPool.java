package me.maurxce.unlockabledimensions.commands;

import me.maurxce.unlockabledimensions.Main;
import me.maurxce.unlockabledimensions.managers.FileManager;
import me.maurxce.unlockabledimensions.services.Database;
import me.maurxce.unlockabledimensions.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;

public class ResetPool implements CommandExecutor {

    private final FileConfiguration lang = FileManager.getLang();
    private Database database = Main.instance.getDbManager().getDatabase();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !sender.hasPermission("dimension.reload")) {
            String noPermission = lang.getString("no-permission");

            sender.sendMessage(ChatUtils.translate(noPermission));
            return true;
        }

        database.setPaid(0);

        try {
            FileManager.reloadFiles(true);

            Bukkit.getLogger().warning("Reset dimensions pool");
            if (sender instanceof Player) {
                String reloaded = lang.getString("successful-reset");
                sender.sendMessage(ChatUtils.translate(reloaded));
            }
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
