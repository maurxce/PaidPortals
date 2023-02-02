package me.maurxce.paidportals.managers;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager implements Listener {

    private static Economy economy = null;

    public static boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) return false;

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;

        economy = rsp.getProvider();
        return economy != null;
    }

    public static boolean hasBalance(Player player, double amout) {
        return economy.has(player, amout);
    }

    public static void withdraw(Player player, double amount) {
        economy.withdrawPlayer(player, amount);
    }
}
