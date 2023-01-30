package me.maurxce.unlockabledimensions.commands;

import me.maurxce.unlockabledimensions.Main;
import me.maurxce.unlockabledimensions.managers.EconomyManager;
import me.maurxce.unlockabledimensions.managers.FileManager;
import me.maurxce.unlockabledimensions.services.Database;
import me.maurxce.unlockabledimensions.utils.ChatUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DimensionPay implements CommandExecutor, TabCompleter {

    private final FileConfiguration config = FileManager.getConfig();
    private final FileConfiguration lang = FileManager.getLang();
    private Database database = Main.instance.getDbManager().getDatabase();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (!player.hasPermission("dimension.dimensionpay")) {
            String noPermission = lang.getString("no-permission");

            player.sendMessage(ChatUtils.translate(noPermission));
            return true;
        }

        if (args.length < 1) {
            String missingArgs = lang.getString("not-enough-args")
                    .replace("%usage%", getUsage(command, label));

            player.sendMessage(ChatUtils.translate(missingArgs));
            return true;
        }

        int amount = 0;
        boolean validArgs = true;

        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            validArgs = false;
        }

        if (!validArgs || amount <= 0) {
            String invalidArgs = lang.getString("invalid-args")
                    .replace("%usage%", getUsage(command, label));

            player.sendMessage(ChatUtils.translate(invalidArgs));
            return true;
        }

        if (!EconomyManager.hasBalance(player, amount)) {
            String insufficientFunds = lang.getString("insufficient-funds");

            player.sendMessage(ChatUtils.translate(insufficientFunds));
            return true;
        }

        payIntoPool(player, amount);

        boolean enabledNether = config.getBoolean("nether.enable");
        boolean enabledEnd = config.getBoolean("the_end.enable");

        if (enabledNether) unlockDimension("nether");
        if (enabledEnd) unlockDimension("the_end");

        try {
            FileManager.reloadFiles(true);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public String getUsage(Command command, String label) {
        return command.getUsage().replace("<command>", label);
    }

    public void payIntoPool(Player player, int amount) {
        EconomyManager.withdraw(player, amount);
        database.addPaid(amount);

        try {
            FileManager.reloadFiles(true);

            String playerPaid = lang.getString("player-paid")
                    .replace("%username%", player.getName())
                    .replace("%amount%", String.valueOf(amount));

            Bukkit.broadcastMessage(ChatUtils.translate(playerPaid));
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public void unlockDimension(String dimension) {
        String name = WordUtils.capitalizeFully(dimension.replace("_", " "));

        int goal = config.getInt(dimension + ".unlock-amount");
        boolean locked = database.isLocked(dimension);
        boolean canUnlock = database.getPaid() >= goal;

        if (locked && canUnlock) {
            String dimensionUnlocked = lang.getString("dimension-unlocked")
                    .replace("%dimension%", name);

            Bukkit.broadcastMessage(ChatUtils.translate(dimensionUnlocked));

            database.unlockDimension(dimension);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        final String[] amount = { "amount" };
        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], Arrays.asList(amount), completions);

        return completions;
    }
}
