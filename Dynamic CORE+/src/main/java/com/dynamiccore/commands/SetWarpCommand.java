package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public SetWarpCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(plugin.getConfigManager().getEssentialsConfig().getString("setwarp.usage", "Usage: /setwarp <name>"));
            return true;
        }

        Player player = (Player) sender;
        String warpName = args[0];

        if (plugin.getPlayerDataManager().getWarp(warpName) != null) {
    player.sendMessage(
        plugin.getConfigManager()
              .getEssentialsConfig()
              .getString("setwarp.exists", "Warp '%warp%' already exists!")
              .replace("%warp%", warpName)
    );
    return true;
}


        plugin.getPlayerDataManager().setWarp(warpName, player.getLocation());
        player.sendMessage(plugin.getConfigManager().getEssentialsConfig().getString("setwarp.set", "Warp '%warp%' set!").replace("%warp%", warpName));
        return true;
    }
}