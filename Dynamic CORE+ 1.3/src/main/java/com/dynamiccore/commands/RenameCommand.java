package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RenameCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public RenameCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(plugin.getConfigManager().colorize("&cUsage: /rename <name>"));
            return true;
        }

        Player player = (Player) sender;
        String name = String.join(" ", args);

        // Placeholder: Rename item
        player.sendMessage(plugin.getConfigManager().colorize("&aRenamed item to '&f" + name + "&a'!"));
        return true;
    }
}
