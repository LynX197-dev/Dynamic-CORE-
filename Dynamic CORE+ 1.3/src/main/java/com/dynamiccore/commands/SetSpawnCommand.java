package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public SetSpawnCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        Player player = (Player) sender;

        plugin.getPlayerDataManager().setSpawn(player.getLocation());
        player.sendMessage(plugin.getConfigManager().colorize(
            plugin.getConfigManager().getEssentialsConfig()
                .getString("setspawn.set", "&aSpawn set!")
        ));
        return true;
    }
}
