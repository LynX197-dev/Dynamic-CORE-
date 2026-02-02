package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EcoCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public EcoCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("dynamiccore+.moderation.economy.admin")) {
            sender.sendMessage("You don't have permission to use this command.");
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage("Usage: /eco <give|take|set|reset> <player> <amount>");
            return true;
        }

        String action = args[0].toLowerCase();
        Player target = plugin.getServer().getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage("Player not found.");
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid amount.");
            return true;
        }

        switch (action) {
            case "give":
                plugin.getPlayerDataManager().addBalance(target, amount);
                sender.sendMessage("Gave " + amount + " to " + target.getName());
                break;
            case "take":
                if (plugin.getPlayerDataManager().withdrawBalance(target, amount)) {
                    sender.sendMessage("Took " + amount + " from " + target.getName());
                } else {
                    sender.sendMessage("Player doesn't have enough funds.");
                }
                break;
            case "set":
                plugin.getPlayerDataManager().setBalance(target, amount);
                sender.sendMessage("Set " + target.getName() + "'s balance to " + amount);
                break;
            case "reset":
                plugin.getPlayerDataManager().setBalance(target, 0);
                sender.sendMessage("Reset " + target.getName() + "'s balance to 0");
                break;
            default:
                sender.sendMessage("Invalid action. Use give, take, set, or reset.");
                break;
        }
        return true;
    }
}