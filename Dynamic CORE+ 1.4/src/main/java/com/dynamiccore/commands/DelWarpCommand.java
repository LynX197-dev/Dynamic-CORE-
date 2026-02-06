package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelWarpCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public DelWarpCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Console check
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        Player player = (Player) sender;

        // Permission check
        if (!player.hasPermission("dynamiccore+.moderation.delwarp")) {
            player.sendMessage(plugin.getConfigManager().getMessage("no-permission"));
            return true;
        }

        // Usage check
        if (args.length == 0) {
            player.sendMessage(
                plugin.getConfigManager().colorize(
                    plugin.getConfigManager()
                          .getEssentialsConfig()
                          .getString("delwarp.usage", "&cUsage: /delwarp <name>")
                )
            );
            return true;
        }

        String warpName = args[0];

        // Warp exists check
        if (plugin.getPlayerDataManager().getWarp(warpName) == null) {
            player.sendMessage(
                plugin.getConfigManager().colorize(
                    plugin.getConfigManager()
                          .getEssentialsConfig()
                          .getString("delwarp.not-found", "&cWarp '%warp%' does not exist!")
                          .replace("%warp%", warpName)
                )
            );
            return true;
        }

        // Delete warp
        plugin.getPlayerDataManager().deleteWarp(warpName);

        player.sendMessage(
            plugin.getConfigManager().colorize(
                plugin.getConfigManager()
                      .getEssentialsConfig()
                      .getString("delwarp.deleted", "&aWarp '%warp%' deleted!")
                      .replace("%warp%", warpName)
            )
        );

        return true;
    }
}
