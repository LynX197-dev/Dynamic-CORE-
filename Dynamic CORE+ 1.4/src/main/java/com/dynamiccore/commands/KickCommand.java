package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KickCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public KickCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(plugin.getConfigManager().colorize("&cUsage: /kick <player> [reason]"));
            return true;
        }

        String playerName = args[0];
        String reason = args.length > 1 ? args[1] : "Kicked";

        // Placeholder: Add kick logic
        sender.sendMessage(plugin.getConfigManager().colorize(
            "&aKicked &f" + playerName + " &afor: &e" + reason
        ));
        return true;
    }
}
