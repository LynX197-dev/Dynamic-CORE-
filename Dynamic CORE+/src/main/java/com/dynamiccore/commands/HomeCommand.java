package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;

public class HomeCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public HomeCommand(DynamicCOREPlus plugin) {
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

        Location home = plugin.getPlayerDataManager().getHome(player, homeName);
        if (home == null) {
            player.sendMessage(plugin.getConfigManager().getEssentialsConfig().getString("home.not-found", "Home '%home%' not found!").replace("%home%", homeName));
            return true;
        }

        int delay = plugin.getConfigManager().getInt("essentials.home.teleport-delay", 3);
        player.sendMessage(plugin.getConfigManager().getEssentialsConfig().getString("home.teleporting", "Teleporting to home in %delay% seconds...").replace("%delay%", String.valueOf(delay)));

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            plugin.getPlayerDataManager().setLastLocation(player, player.getLocation());
            player.teleport(home);
            player.sendMessage(plugin.getConfigManager().getEssentialsConfig().getString("home.teleported", "Teleported to home '%home%'!").replace("%home%", homeName));
        }, 20L * delay);

        return true;
    }
}