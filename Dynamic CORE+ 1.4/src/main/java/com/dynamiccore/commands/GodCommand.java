package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public GodCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player target = args.length > 0 ? plugin.getServer().getPlayer(args[0]) : (Player) sender;
        if (target == null) {
            sender.sendMessage(plugin.getConfigManager().colorize("&cPlayer not found!"));
            return true;
        }

        // Placeholder: Toggle god mode
        sender.sendMessage(plugin.getConfigManager().colorize("&aGod mode toggled for &f" + target.getName() + "&a!"));
        return true;
    }
}
