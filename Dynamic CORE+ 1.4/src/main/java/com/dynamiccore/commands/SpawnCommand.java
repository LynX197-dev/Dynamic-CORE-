package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public SpawnCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        Player player = (Player) sender;

        Location spawn = plugin.getPlayerDataManager().getSpawn();
        if (spawn == null) {
            player.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("spawn.not-set", "&cSpawn not set!")
            ));
            return true;
        }

        int delay = plugin.getConfigManager().getInt("essentials.spawn.teleport-delay", 3);
        if (delay <= 0) {
            plugin.getPlayerDataManager().setLastLocation(player, player.getLocation());
            player.teleport(spawn);
            player.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("spawn.teleported", "&aTeleported to spawn!")
            ));
            return true;
        }

        new BukkitRunnable() {
            int remaining = delay;

            @Override
            public void run() {
                if (remaining <= 0) {
                    plugin.getPlayerDataManager().setLastLocation(player, player.getLocation());
                    player.teleport(spawn);
                    player.sendMessage(plugin.getConfigManager().colorize(
                        plugin.getConfigManager().getEssentialsConfig()
                            .getString("spawn.teleported", "&aTeleported to spawn!")
                    ));
                    cancel();
                    return;
                }

                player.sendMessage(plugin.getConfigManager().colorize(
                    plugin.getConfigManager().getEssentialsConfig()
                        .getString("spawn.teleporting", "&eTeleporting to spawn in &f%delay%&e seconds...")
                        .replace("%delay%", String.valueOf(remaining))
                ));
                remaining--;
            }
        }.runTaskTimer(plugin, 0L, 20L);

        return true;
    }
}
