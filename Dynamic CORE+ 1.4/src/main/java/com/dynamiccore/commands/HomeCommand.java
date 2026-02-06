package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

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
            player.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("home.not-found", "&cHome '%home%' not found!")
                    .replace("%home%", homeName)
            ));
            return true;
        }

        int delay = plugin.getConfigManager().getInt("essentials.home.teleport-delay", 3);
        if (delay <= 0) {
            plugin.getPlayerDataManager().setLastLocation(player, player.getLocation());
            player.teleport(home);
            player.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("home.teleported", "&aTeleported to home '&f%home%&a'!")
                    .replace("%home%", homeName)
            ));
            return true;
        }

        new BukkitRunnable() {
            int remaining = delay;

            @Override
            public void run() {
                if (remaining <= 0) {
                    plugin.getPlayerDataManager().setLastLocation(player, player.getLocation());
                    player.teleport(home);
                    player.sendMessage(plugin.getConfigManager().colorize(
                        plugin.getConfigManager().getEssentialsConfig()
                            .getString("home.teleported", "&aTeleported to home '&f%home%&a'!")
                            .replace("%home%", homeName)
                    ));
                    cancel();
                    return;
                }

                player.sendMessage(plugin.getConfigManager().colorize(
                    plugin.getConfigManager().getEssentialsConfig()
                        .getString("home.teleporting", "&eTeleporting to home in &f%delay%&e seconds...")
                        .replace("%delay%", String.valueOf(remaining))
                ));
                remaining--;
            }
        }.runTaskTimer(plugin, 0L, 20L);

        return true;
    }
}
