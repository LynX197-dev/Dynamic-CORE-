package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

public class LeaderboardCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public LeaderboardCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || !args[0].equalsIgnoreCase("economy")) {
            sender.sendMessage("Usage: /leaderboard economy");
            return true;
        }

        Map<String, Double> balances = new HashMap<>();
        for (OfflinePlayer op : plugin.getServer().getOfflinePlayers()) {
            double bal = plugin.getPlayerDataManager().getBalance(op.getPlayer());
            if (bal > 0) {
                balances.put(op.getName(), bal);
            }
        }

        List<Map.Entry<String, Double>> sorted = new ArrayList<>(balances.entrySet());
        sorted.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        sender.sendMessage("Economy Leaderboard:");
        int max = plugin.getConfigManager().getEconomyConfig().getInt("economy-leaderboard.max-entries", 10);
        for (int i = 0; i < Math.min(max, sorted.size()); i++) {
            Map.Entry<String, Double> entry = sorted.get(i);
            sender.sendMessage((i+1) + ". " + entry.getKey() + " - $" + String.format("%.2f", entry.getValue()));
        }
        return true;
    }
}