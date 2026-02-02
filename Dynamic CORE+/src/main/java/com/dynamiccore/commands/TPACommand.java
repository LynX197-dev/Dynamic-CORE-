package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import com.dynamiccore.TPARequest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TPACommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public TPACommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        Player player = (Player) sender;

        if (label.equalsIgnoreCase("tpa")) {
            if (args.length != 1) {
                player.sendMessage(plugin.getConfigManager().getMessage("tpa-usage"));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(plugin.getConfigManager().getMessage("player-not-found"));
                return true;
            }
            if (target.equals(player)) {
                player.sendMessage(plugin.getConfigManager().getMessage("tpa-self"));
                return true;
            }
            // Check cooldown
            // TODO: implement cooldown logic using config
            plugin.getPendingTPARequests().put(target.getUniqueId(), new TPARequest(player.getUniqueId(), target.getUniqueId(), TPARequest.Type.TPA));
            player.sendMessage(plugin.getConfigManager().getMessage("tpa-sent").replace("%player%", target.getName()));
            target.sendMessage(plugin.getConfigManager().getMessage("tpa-received").replace("%player%", player.getName()));
            // Expire after time
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (plugin.getPendingTPARequests().containsKey(target.getUniqueId()) &&
                        plugin.getPendingTPARequests().get(target.getUniqueId()).getRequester().equals(player.getUniqueId()) &&
                        plugin.getPendingTPARequests().get(target.getUniqueId()).getType() == TPARequest.Type.TPA) {
                        plugin.getPendingTPARequests().remove(target.getUniqueId());
                        player.sendMessage(plugin.getConfigManager().getMessage("request-expired"));
                        target.sendMessage(plugin.getConfigManager().getMessage("request-expired-target").replace("%player%", player.getName()));
                    }
                }
            }.runTaskLater(plugin, 20 * 60); // 60 seconds
            return true;
        } else if (label.equalsIgnoreCase("tpahere")) {
            if (args.length != 1) {
                player.sendMessage(plugin.getConfigManager().getMessage("tpahere-usage"));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(plugin.getConfigManager().getMessage("player-not-found"));
                return true;
            }
            if (target.equals(player)) {
                player.sendMessage(plugin.getConfigManager().getMessage("tpahere-self"));
                return true;
            }
            // Check cooldown
            plugin.getPendingTPARequests().put(target.getUniqueId(), new TPARequest(player.getUniqueId(), target.getUniqueId(), TPARequest.Type.TPAHERE));
            player.sendMessage(plugin.getConfigManager().getMessage("tpahere-sent").replace("%player%", target.getName()));
            target.sendMessage(plugin.getConfigManager().getMessage("tpahere-received").replace("%player%", player.getName()));
            // Expire after time
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (plugin.getPendingTPARequests().containsKey(target.getUniqueId()) &&
                        plugin.getPendingTPARequests().get(target.getUniqueId()).getRequester().equals(player.getUniqueId()) &&
                        plugin.getPendingTPARequests().get(target.getUniqueId()).getType() == TPARequest.Type.TPAHERE) {
                        plugin.getPendingTPARequests().remove(target.getUniqueId());
                        player.sendMessage(plugin.getConfigManager().getMessage("request-expired-here"));
                        target.sendMessage(plugin.getConfigManager().getMessage("tpahere-expired-target").replace("%player%", player.getName()));
                    }
                }
            }.runTaskLater(plugin, 20 * 60); // 60 seconds
            return true;
        }

        return false;
    }


}