package me.maurxce.paidportals.commands;

import me.maurxce.paidportals.Main;
import me.maurxce.paidportals.managers.FileManager;
import me.maurxce.paidportals.services.Database;
import me.maurxce.paidportals.utils.ChatUtils;
import me.maurxce.paidportals.utils.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ResetPool implements CommandExecutor {

    private final FileConfiguration lang = FileManager.getLang();
    private final Database database = Main.instance.getDatabase();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !sender.hasPermission("paidportals.reload")) {
            String noPermission = lang.getString("no-permission");

            sender.sendMessage(ChatUtils.translate(noPermission));
            return true;
        }

        database.setPaid(0);

        Logger.warning("Reset dimensions pool");
        if (sender instanceof Player) {
            String reloaded = lang.getString("successful-reset");
            sender.sendMessage(ChatUtils.translate(reloaded));
        }

        return true;
    }
}
