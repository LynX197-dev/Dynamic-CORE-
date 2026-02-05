package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnchantCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public EnchantCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(plugin.getConfigManager().colorize("&cUsage: /enchant <enchantment> <level>"));
            return true;
        }

        Player player = (Player) sender;
        String enchantName = args[0];
        int level;

        try {
            level = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(plugin.getConfigManager().colorize("&cInvalid level!"));
            return true;
        }

        // Placeholder: Enchant item
        player.sendMessage(plugin.getConfigManager().colorize(
            "&aEnchanted item with &f" + enchantName + " " + level + "&a!"
        ));
        return true;
    }
}
