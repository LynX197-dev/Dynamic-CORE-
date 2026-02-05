package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FreezeCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public FreezeCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(plugin.getConfigManager().colorize("&cUsage: /freeze <player>"));
            return true;
        }

        String playerName = args[0];

        // Placeholder: Toggle freeze
        sender.sendMessage(plugin.getConfigManager().colorize("&aFroze &f" + playerName + "&a!"));
        return true;
    }
}
