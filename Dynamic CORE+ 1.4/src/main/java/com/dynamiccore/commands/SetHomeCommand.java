package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class SetHomeCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public SetHomeCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        Player player = (Player) sender;
        String homeName = args.length > 0 ? args[0] : "home";

        int maxHomes = plugin.getConfigManager().getInt("essentials.home.max-homes", 3);
        Map<String, Location> homes = plugin.getPlayerDataManager().getHomes(player);
        if (homes.size() >= maxHomes && !homes.containsKey(homeName)) {
            player.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("sethome.max-homes", "&cYou can only have &f%max%&c homes!")
                    .replace("%max%", String.valueOf(maxHomes))
            ));
            return true;
        }

        plugin.getPlayerDataManager().setHome(player, homeName, player.getLocation());
        player.sendMessage(plugin.getConfigManager().colorize(
            plugin.getConfigManager().getEssentialsConfig()
                .getString("sethome.set", "&aHome '&f%home%&a' set!")
                .replace("%home%", homeName)
        ));
        return true;
    }
}
