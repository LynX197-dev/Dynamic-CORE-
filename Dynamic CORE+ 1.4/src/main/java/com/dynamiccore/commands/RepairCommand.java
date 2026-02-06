package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RepairCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public RepairCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Implement repair logic
        sender.sendMessage(plugin.getConfigManager().colorize("&aRepair command executed."));
        return true;
    }
}
