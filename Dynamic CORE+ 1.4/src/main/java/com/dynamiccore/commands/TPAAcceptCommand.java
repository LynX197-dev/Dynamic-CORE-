package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import com.dynamiccore.TPARequest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TPAAcceptCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public TPAAcceptCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        Player player = (Player) sender;
        TPARequest request = plugin.getPendingTPARequests().get(player.getUniqueId());
        if (request == null) {
            player.sendMessage(plugin.getConfigManager().getMessage("no-pending-tpa"));
            return true;
        }

        plugin.getPendingTPARequests().remove(player.getUniqueId());
        Player requester = Bukkit.getPlayer(request.getRequester());
        if (requester == null) {
            player.sendMessage(plugin.getConfigManager().getMessage("player-not-found"));
            return true;
        }

        int delay = plugin.getConfigManager().getInt("essentials.tpa.tpa-delay", 3);
        if (request.getType() == TPARequest.Type.TPA) {
            player.sendMessage(plugin.getConfigManager().getMessage("request-accepted").replace("%delay%", String.valueOf(delay)));
            if (delay <= 0) {
                plugin.getPlayerDataManager().setLastLocation(requester, requester.getLocation());
                requester.teleport(player);
                requester.sendMessage(plugin.getConfigManager().getMessage("teleported"));
                return true;
            }
            String countdownMessage = plugin.getConfigManager().getMessage("request-accepted-requester");
            new BukkitRunnable() {
                int remaining = delay;
                @Override
                public void run() {
                    if (remaining <= 0) {
                        plugin.getPlayerDataManager().setLastLocation(requester, requester.getLocation());
                        requester.teleport(player);
                        requester.sendMessage(plugin.getConfigManager().getMessage("teleported"));
                        cancel();
                        return;
                    }
                    requester.sendMessage(countdownMessage.replace("%delay%", String.valueOf(remaining)));
                    remaining--;
                }
            }.runTaskTimer(plugin, 0L, 20L);
        } else { // TPAHERE
            requester.sendMessage(plugin.getConfigManager().getMessage("tpahere-accepted-requester").replace("%delay%", String.valueOf(delay)));
            if (delay <= 0) {
                plugin.getPlayerDataManager().setLastLocation(player, player.getLocation());
                player.teleport(requester);
                player.sendMessage(plugin.getConfigManager().getMessage("teleported"));
                return true;
            }
            String countdownMessage = plugin.getConfigManager().getMessage("request-accepted-here");
            new BukkitRunnable() {
                int remaining = delay;
                @Override
                public void run() {
                    if (remaining <= 0) {
                        plugin.getPlayerDataManager().setLastLocation(player, player.getLocation());
                        player.teleport(requester);
                        player.sendMessage(plugin.getConfigManager().getMessage("teleported"));
                        cancel();
                        return;
                    }
                    player.sendMessage(countdownMessage.replace("%delay%", String.valueOf(remaining)));
                    remaining--;
                }
            }.runTaskTimer(plugin, 0L, 20L);
        }

        return true;
    }
}
