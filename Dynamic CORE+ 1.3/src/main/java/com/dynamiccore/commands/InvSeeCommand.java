package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvSeeCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public InvSeeCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getModerationConfig()
                    .getString("invsee.messages.console", "&cOnly players can use this command.")
            ));
            return true;
        }

        Player admin = (Player) sender;

        if (!admin.hasPermission("dynamiccore+.admin.invsee")) {
            admin.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getModerationConfig()
                    .getString("invsee.messages.no-permission", "&cYou don't have permission to use this command.")
            ));
            return true;
        }

        if (args.length != 1) {
            admin.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getModerationConfig()
                    .getString("invsee.messages.usage", "&cUsage: /invsee <player>")
            ));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            admin.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getModerationConfig()
                    .getString("invsee.messages.player-not-found", "&cPlayer not found.")
            ));
            return true;
        }

        if (target.equals(admin) && !plugin.getConfigManager().getModerationConfig().getBoolean("invsee.allow-self", false)) {
            admin.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getModerationConfig()
                    .getString("invsee.messages.cannot-see-self", "&cYou cannot view your own inventory.")
            ));
            return true;
        }

        admin.openInventory(target.getInventory());
        admin.sendMessage(plugin.getConfigManager().colorize(
            plugin.getConfigManager().getModerationConfig()
                .getString("invsee.messages.opened", "&aOpened inventory of &e%player%&a.")
                .replace("%player%", target.getName())
        ));
        if (plugin.getConfigManager().getModerationConfig().getBoolean("invsee.notify-target", false)) {
            target.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getModerationConfig()
                    .getString("invsee.messages.target-notified", "&e%admin% &ais viewing your inventory.")
                    .replace("%admin%", admin.getName())
            ));
        }

        return true;
    }
}
