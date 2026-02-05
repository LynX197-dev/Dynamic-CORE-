package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import com.dynamiccore.TPARequest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPADenyCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public TPADenyCommand(DynamicCOREPlus plugin) {
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
        if (requester != null) {
            if (request.getType() == TPARequest.Type.TPA) {
                player.sendMessage(plugin.getConfigManager().getMessage("request-denied"));
                requester.sendMessage(plugin.getConfigManager().getMessage("request-denied-requester").replace("%player%", player.getName()));
            } else {
                player.sendMessage(plugin.getConfigManager().getMessage("request-denied-here"));
                requester.sendMessage(plugin.getConfigManager().getMessage("tpahere-denied-requester").replace("%player%", player.getName()));
            }
        }

        return true;
    }
}