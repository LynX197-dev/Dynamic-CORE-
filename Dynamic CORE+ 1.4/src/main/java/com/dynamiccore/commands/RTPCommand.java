package com.dynamiccore.commands;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class RTPCommand implements CommandExecutor {

    private final DynamicCOREPlus plugin;

    public RTPCommand(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getMessage("only-players"));
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();
        Random random = new Random();

        int maxRange = plugin.getConfigManager().getInt("essentials.rtp.max-range", 10000);
        int minRange = plugin.getConfigManager().getInt("essentials.rtp.min-range", 1000);
        int maxTries = plugin.getConfigManager().getInt("essentials.rtp.max-tries", 10);

        Location randomLocation = null;
        for (int i = 0; i < maxTries; i++) {
            int x = random.nextInt(maxRange * 2) - maxRange;
            int z = random.nextInt(maxRange * 2) - maxRange;
            if (Math.sqrt(x * x + z * z) < minRange) continue; // Too close

            int y = world.getHighestBlockYAt(x, z);
            Location loc = new Location(world, x, y + 1, z);

            // Check if safe
            Block block = loc.getBlock();
            Block below = loc.clone().subtract(0, 1, 0).getBlock();
            Block above = loc.clone().add(0, 1, 0).getBlock();

            if (block.getType() == Material.AIR && above.getType() == Material.AIR && below.getType().isSolid()) {
                randomLocation = loc;
                break;
            }
        }

        if (randomLocation == null) {
            player.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("rtp.no-safe-location", "&cCould not find a safe location to teleport!")
            ));
            return true;
        }

        final Location finalLocation = randomLocation;

        int delay = plugin.getConfigManager().getInt("essentials.rtp.teleport-delay", 3);
        player.sendMessage(plugin.getConfigManager().colorize(
            plugin.getConfigManager().getEssentialsConfig()
                .getString("rtp.teleporting", "&eRandom teleporting in &f%delay%&e seconds...")
                .replace("%delay%", String.valueOf(delay))
        ));

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            player.teleport(finalLocation);
            player.sendMessage(plugin.getConfigManager().colorize(
                plugin.getConfigManager().getEssentialsConfig()
                    .getString("rtp.teleported", "&aRandom teleported!")
            ));
        }, 20L * delay);

        return true;
    }
}
