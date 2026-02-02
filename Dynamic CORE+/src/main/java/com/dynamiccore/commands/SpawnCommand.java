package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;

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
            player.sendMessage(plugin.getConfigManager().getEssentialsConfig().getString("spawn.not-set", "Spawn not set!"));
            return true;
        }

        int delay = plugin.getConfigManager().getInt("essentials.spawn.teleport-delay", 3);
        player.sendMessage(plugin.getConfigManager().getEssentialsConfig().getString("spawn.teleporting", "Teleporting to spawn in %delay% seconds...").replace("%delay%", String.valueOf(delay)));

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            plugin.getPlayerDataManager().setLastLocation(player, player.getLocation());
            player.teleport(spawn);
            player.sendMessage(plugin.getConfigManager().getEssentialsConfig().getString("spawn.teleported", "Teleported to spawn!"));
        }, 20L * delay);

        return true;
    }
}