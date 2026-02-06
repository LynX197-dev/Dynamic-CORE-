package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public BalanceCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().colorize("&cThis command can only be used by players."));
            return true;
        }

        Player player = (Player) sender;
        double balance = plugin.getPlayerDataManager().getBalance(player);
        String currency = plugin.getConfigManager().getEconomyConfig().getString("balance.currency-symbol", "$");
        player.sendMessage(plugin.getConfigManager().colorize(
            "&aYour balance: &f" + currency + String.format("%.2f", balance)
        ));
        return true;
    }
}
