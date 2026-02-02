package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public StaffChatCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /staffchat <message>");
            return true;
        }

        String message = String.join(" ", args);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("dynamiccore+.moderation.staffchat")) {
                player.sendMessage("[Staff] " + sender.getName() + ": " + message);
            }
        }
        return true;
    }
}