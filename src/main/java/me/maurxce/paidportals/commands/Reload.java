package me.maurxce.paidportals.commands;

import me.maurxce.paidportals.managers.FileManager;
import me.maurxce.paidportals.utils.ChatUtils;
import me.maurxce.paidportals.utils.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Reload implements CommandExecutor {

    private final FileConfiguration lang = FileManager.getLang();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !sender.hasPermission("paidportals.reload")) {
            String noPermission = lang.getString("no-permission");

            sender.sendMessage(ChatUtils.translate(noPermission));
            return true;
        }

        try {
            FileManager.reloadFiles(false);

            Logger.warning("Reloaded config files");
            if (sender instanceof Player) {
                String reloaded = lang.getString("successful-reload");
                sender.sendMessage(ChatUtils.translate(reloaded));
            }
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
