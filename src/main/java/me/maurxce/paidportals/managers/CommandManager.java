package me.maurxce.paidportals.managers;

import me.maurxce.paidportals.Main;
import me.maurxce.paidportals.commands.DimensionPay;
import me.maurxce.paidportals.commands.Reload;

public class CommandManager {

    private static final Main main = Main.instance;

    public static void register() {
        main.getCommand("reload").setExecutor(new Reload());
        main.getCommand("resetpool").setExecutor(new Reload());

        main.getCommand("dpay").setExecutor(new DimensionPay());
    }
}
