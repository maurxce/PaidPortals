package me.maurxce.unlockabledimensions.managers;

import me.maurxce.unlockabledimensions.Main;
import me.maurxce.unlockabledimensions.commands.DimensionPay;
import me.maurxce.unlockabledimensions.commands.Reload;

public class CommandManager {

    private static final Main main = Main.instance;

    public static void register() {
        main.getCommand("reload").setExecutor(new Reload());
        main.getCommand("resetpool").setExecutor(new Reload());

        main.getCommand("dpay").setExecutor(new DimensionPay());
    }
}
