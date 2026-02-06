package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public BackCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        Player player = (Player) sender;

        Location lastLocation = plugin.getPlayerDataManager().getLastLocation(player);
        if (lastLocation == null) {
            player.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("back.no-location", "&cNo previous location found!")
            ));
            return true;
        }

        int delay = plugin.getConfigManager().getInt("essentials.back.teleport-delay", 3);
        player.sendMessage(plugin.getConfigManager().colorize(
            plugin.getConfigManager().getEssentialsConfig()
                .getString("back.teleporting", "&eTeleporting back in &f%delay%&e seconds...")
                .replace("%delay%", String.valueOf(delay))
        ));

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            player.teleport(lastLocation);
            player.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("back.teleported", "&aTeleported back to previous location!")
            ));
        }, 20L * delay);

        return true;
    }
}
