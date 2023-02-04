package me.maurxce.paidportals.managers;

import me.maurxce.paidportals.Main;
import me.maurxce.paidportals.commands.*;

public class CommandManager {

    private static final Main main = Main.instance;

    public static void register() {
        main.getCommand("reload").setExecutor(new Reload());
        main.getCommand("resetpool").setExecutor(new ResetPool());

        main.getCommand("viewpool").setExecutor(new ViewPool());
        main.getCommand("dpay").setExecutor(new DimensionPay());

        main.getCommand("paidportals").setExecutor(new PaidPortals());
    }
}
