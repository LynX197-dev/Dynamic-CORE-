package com.dynamiccore.managers;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private final DynamicCOREPlus plugin;
    private FileConfiguration essentialsConfig;
    private FileConfiguration economyConfig;
    private FileConfiguration moderationConfig;
    private FileConfiguration utilityConfig;
    private FileConfiguration qolConfig;

    public ConfigManager(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        createConfig("essentials.yml");
        createConfig("economy.yml");
        createConfig("moderation.yml");
        createConfig("utility.yml");
        createConfig("qol.yml");

        essentialsConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "essentials.yml"));
        economyConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "economy.yml"));
        moderationConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "moderation.yml"));
        utilityConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "utility.yml"));
        qolConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "qol.yml"));
    }

    private void createConfig(String name) {
        File file = new File(plugin.getDataFolder(), name);
        if (!file.exists()) {
            plugin.saveResource(name, false);
        }
    }

    public FileConfiguration getEssentialsConfig() {
        return essentialsConfig;
    }

    public FileConfiguration getEconomyConfig() {
        return economyConfig;
    }

    public FileConfiguration getModerationConfig() {
        return moderationConfig;
    }

    public FileConfiguration getUtilityConfig() {
        return utilityConfig;
    }

    public FileConfiguration getQolConfig() {
        return qolConfig;
    }

    public String getMessage(String key) {
        return essentialsConfig.getString(key);
    }

    public int getInt(String key, int defaultValue) {
        return essentialsConfig.getInt(key, defaultValue);
    }

    public void saveConfig(String name) {
        try {
            switch (name) {
                case "essentials":
                    essentialsConfig.save(new File(plugin.getDataFolder(), "essentials.yml"));
                    break;
                case "economy":
                    economyConfig.save(new File(plugin.getDataFolder(), "economy.yml"));
                    break;
                case "moderation":
                    moderationConfig.save(new File(plugin.getDataFolder(), "moderation.yml"));
                    break;
                case "utility":
                    utilityConfig.save(new File(plugin.getDataFolder(), "utility.yml"));
                    break;
                case "qol":
                    qolConfig.save(new File(plugin.getDataFolder(), "qol.yml"));
                    break;
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save config: " + name);
        }
    }
}