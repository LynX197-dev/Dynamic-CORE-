package com.dynamiccore;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import com.dynamiccore.managers.ConfigManager;
import com.dynamiccore.managers.CommandManager;
import com.dynamiccore.managers.PlayerDataManager;
import com.dynamiccore.listeners.PlayerListener;
import com.dynamiccore.economy.VaultEconomyProvider;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DynamicCOREPlus extends JavaPlugin {

    private static DynamicCOREPlus instance;
    private ConfigManager configManager;
    private PlayerDataManager playerDataManager;
    private Map<UUID, TPARequest> pendingTPARequests = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager(this);
        configManager.loadConfigs();
        playerDataManager = new PlayerDataManager(this);

        new CommandManager(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        // Register Vault economy if Vault is present
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                getServer().getServicesManager().register(Economy.class, new VaultEconomyProvider(this), this, org.bukkit.plugin.ServicePriority.Normal);
                getLogger().info("Vault economy integration registered!");
            }
        }

        getLogger().info("Dynamic CORE+ enabled with built-in economy!");

      // ASCII
        Bukkit.getConsoleSender().sendMessage(AsciiBanner.DESIGN_CREDIT);
    }

    @Override
    public void onDisable() {
        getLogger().info("Dynamic CORE+ disabled!");
    }

    public static DynamicCOREPlus getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public Map<UUID, TPARequest> getPendingTPARequests() {
        return pendingTPARequests;
    }
}