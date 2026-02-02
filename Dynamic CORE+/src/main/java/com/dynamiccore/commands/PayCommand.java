package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public PayCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(plugin.getConfigManager().getEconomyConfig().getString("pay.usage", "Usage: /pay <player> <amount>"));
            return true;
        }

        Player player = (Player) sender;
        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(plugin.getConfigManager().getMessage("player-not-found"));
            return true;
        }

        if (player.equals(target)) {
            player.sendMessage(plugin.getConfigManager().getEconomyConfig().getString("pay.self-pay", "You cannot pay yourself."));
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(plugin.getConfigManager().getEconomyConfig().getString("pay.invalid-amount", "Invalid amount!"));
            return true;
        }

        if (amount <= 0) {
            player.sendMessage(plugin.getConfigManager().getEconomyConfig().getString("pay.positive-amount", "Amount must be positive!"));
            return true;
        }

        double minAmount = plugin.getConfigManager().getEconomyConfig().getDouble("pay.min-amount", 0.01);
        double maxAmount = plugin.getConfigManager().getEconomyConfig().getDouble("pay.max-amount", 10000.0);
        if (amount < minAmount) {
            player.sendMessage(plugin.getConfigManager().getEconomyConfig().getString("pay.min-amount", "Minimum payment is %amount%.").replace("%amount%", String.valueOf(minAmount)));
            return true;
        }
        if (amount > maxAmount) {
            player.sendMessage(plugin.getConfigManager().getEconomyConfig().getString("pay.max-amount", "Maximum payment is %amount%.").replace("%amount%", String.valueOf(maxAmount)));
            return true;
        }

        if (!plugin.getPlayerDataManager().withdrawBalance(player, amount)) {
            player.sendMessage(plugin.getConfigManager().getEconomyConfig().getString("pay.insufficient-funds", "Insufficient funds!"));
            return true;
        }

        plugin.getPlayerDataManager().addBalance(target, amount);

        player.sendMessage(plugin.getConfigManager().getEconomyConfig().getString("pay.sent", "You paid %target% $%amount%!")
                .replace("%target%", target.getName()).replace("%amount%", String.valueOf(amount)));
        target.sendMessage(plugin.getConfigManager().getEconomyConfig().getString("pay.received", "%sender% paid you $%amount%!")
                .replace("%sender%", player.getName()).replace("%amount%", String.valueOf(amount)));

        return true;
    }
}