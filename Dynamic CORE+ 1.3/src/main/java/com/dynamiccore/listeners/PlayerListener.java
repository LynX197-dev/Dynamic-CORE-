package com.dynamiccore.listeners;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final DynamicCOREPlus plugin;

    public PlayerListener(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        plugin.getPlayerDataManager().setLastDeath(player, player.getLocation());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // For AFK, we can track movement, but for simplicity, perhaps use a task to check activity
        // For now, skip AFK logic as it requires more complex implementation
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getPlayerDataManager().setAFK(player, false); // Reset AFK on quit
    }
}