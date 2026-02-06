package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class WarpCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public WarpCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("warp.usage", "&cUsage: /warp <name>")
            ));
            return true;
        }

        Player player = (Player) sender;
        String warpName = args[0];

        Location warp = plugin.getPlayerDataManager().getWarp(warpName);
        if (warp == null) {
            player.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("warp.not-found", "&cWarp '%warp%' not found!")
                    .replace("%warp%", warpName)
            ));
            return true;
        }

        int delay = plugin.getConfigManager().getInt("essentials.warp.teleport-delay", 3);
        if (delay <= 0) {
            plugin.getPlayerDataManager().setLastLocation(player, player.getLocation());
            player.teleport(warp);
            player.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("warp.teleported", "&aTeleported to warp '&f%warp%&a'!")
                    .replace("%warp%", warpName)
            ));
            return true;
        }

        new BukkitRunnable() {
            int remaining = delay;

            @Override
            public void run() {
                if (remaining <= 0) {
                    plugin.getPlayerDataManager().setLastLocation(player, player.getLocation());
                    player.teleport(warp);
                    player.sendMessage(plugin.getConfigManager().colorize(
                        plugin.getConfigManager().getEssentialsConfig()
                            .getString("warp.teleported", "&aTeleported to warp '&f%warp%&a'!")
                            .replace("%warp%", warpName)
                    ));
                    cancel();
                    return;
                }

                player.sendMessage(plugin.getConfigManager().colorize(
                    plugin.getConfigManager().getEssentialsConfig()
                        .getString("warp.teleporting", "&eTeleporting to warp in &f%delay%&e seconds...")
                        .replace("%delay%", String.valueOf(remaining))
                ));
                remaining--;
            }
        }.runTaskTimer(plugin, 0L, 20L);

        return true;
    }
}
