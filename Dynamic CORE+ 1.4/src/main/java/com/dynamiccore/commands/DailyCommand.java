package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DailyCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public DailyCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        Player player = (Player) sender;

        long lastClaim = plugin.getPlayerDataManager().getLastDailyClaim(player);
        long currentTime = System.currentTimeMillis();
        long cooldownHours = plugin.getConfigManager().getEconomyConfig().getLong("daily-reward.cooldown-hours", 24);
        long cooldownMillis = cooldownHours * 60 * 60 * 1000;

        if (currentTime - lastClaim < cooldownMillis) {
            long remaining = cooldownMillis - (currentTime - lastClaim);
            long hours = remaining / (60 * 60 * 1000);
            long minutes = (remaining % (60 * 60 * 1000)) / (60 * 1000);
            player.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEconomyConfig()
                    .getString("daily-reward.already-claimed", "&cYou can claim again in &f%hours%&c hours and &f%minutes%&c minutes.")
                    .replace("%hours%", String.valueOf(hours))
                    .replace("%minutes%", String.valueOf(minutes))
            ));
            return true;
        }

        double reward = plugin.getConfigManager().getEconomyConfig().getDouble("daily-reward.amount", 100.0);
        plugin.getPlayerDataManager().addBalance(player, reward);
        plugin.getPlayerDataManager().setLastDailyClaim(player, currentTime);

        player.sendMessage(plugin.getConfigManager().colorize(
            plugin.getConfigManager().getEconomyConfig()
                .getString("daily-reward.claimed", "&aDaily reward claimed! You received &f$%amount%&a.")
                .replace("%amount%", String.valueOf(reward))
        ));
        return true;
    }
}
