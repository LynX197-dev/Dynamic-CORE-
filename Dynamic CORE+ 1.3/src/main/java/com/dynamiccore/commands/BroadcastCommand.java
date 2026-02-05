package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;

public class BroadcastCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public BroadcastCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(plugin.getConfigManager().colorize("&cUsage: /broadcast <message>"));
            return true;
        }

        String message = String.join(" ", args);
        Bukkit.broadcastMessage(plugin.getConfigManager().colorize("&6[Broadcast] &f" + message));
        return true;
    }
}
