package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AFKCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public AFKCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        Player player = (Player) sender;

        boolean isAFK = plugin.getPlayerDataManager().isAFK(player);
        plugin.getPlayerDataManager().setAFK(player, !isAFK);

        if (!isAFK) {
            player.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("afk.enabled", "&7You are now AFK.")
            ));
            // Broadcast to others
            plugin.getServer().broadcastMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("afk.broadcast-enabled", "&7%player% is now AFK.")
                    .replace("%player%", player.getName())
            ));
        } else {
            player.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("afk.disabled", "&7You are no longer AFK.")
            ));
            plugin.getServer().broadcastMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("afk.broadcast-disabled", "&7%player% is no longer AFK.")
                    .replace("%player%", player.getName())
            ));
        }

        return true;
    }
}
