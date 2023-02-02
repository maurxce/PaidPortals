package me.maurxce.paidportals.commands;

import me.maurxce.paidportals.Main;
import me.maurxce.paidportals.utils.ChatUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PaidPortals implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        String author = "maurxce";
        String version = Main.instance.getDescription().getVersion();

        TextComponent response = new TextComponent(
                ChatUtils.translate("&5&l[PaidPortals} &dby" + author + " - v" + version)
        );

        response.setClickEvent(
                new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/maurxce/PaidPortals")
        );

        player.spigot().sendMessage(response);
        return true;
    }
}
