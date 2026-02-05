package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHomeCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public DelHomeCommand(DynamicCOREPlus plugin) {
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

        if (plugin.getPlayerDataManager().getHome(player, homeName) == null) {
            player.sendMessage(
                plugin.getConfigManager().colorize(
                    plugin.getConfigManager()
                          .getEssentialsConfig()
                          .getString("delhome.not-found", "&cHome '%home%' not found!")
                          .replace("%home%", homeName)
                )
            );
            return true;
        }

        plugin.getPlayerDataManager().deleteHome(player, homeName);
        player.sendMessage(
            plugin.getConfigManager().colorize(
                plugin.getConfigManager()
                      .getEssentialsConfig()
                      .getString("delhome.deleted", "&aHome '%home%' deleted!")
                      .replace("%home%", homeName)
            )
        );
        return true;
    }
}
