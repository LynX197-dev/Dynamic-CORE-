package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StatsCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public StatsCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String targetName = args.length > 0 ? args[0] : sender.getName();

        // Placeholder: Show stats
        sender.sendMessage("Stats for " + targetName + ": ...");
        return true;
    }
}