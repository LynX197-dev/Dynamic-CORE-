package com.dynamiccore.managers;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PlayerDataManager {

    private final DynamicCOREPlus plugin;
    private FileConfiguration homesConfig;
    private FileConfiguration warpsConfig;
    private FileConfiguration playerDataConfig;
    private File homesFile;
    private File warpsFile;
    private File playerDataFile;

    public PlayerDataManager(DynamicCOREPlus plugin) {
        this.plugin = plugin;
        loadData();
    }

    private void loadData() {
        homesFile = new File(plugin.getDataFolder(), "homes.yml");
        warpsFile = new File(plugin.getDataFolder(), "warps.yml");
        playerDataFile = new File(plugin.getDataFolder(), "playerdata.yml");

        if (!homesFile.exists()) {
            try {
                homesFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create homes.yml");
            }
        }
        if (!warpsFile.exists()) {
            try {
                warpsFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create warps.yml");
            }
        }
        if (!playerDataFile.exists()) {
            try {
                playerDataFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create playerdata.yml");
            }
        }

        homesConfig = YamlConfiguration.loadConfiguration(homesFile);
        warpsConfig = YamlConfiguration.loadConfiguration(warpsFile);
        playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
    }

    public void saveHomes() {
        try {
            homesConfig.save(homesFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save homes.yml");
        }
    }

    public void saveWarps() {
        try {
            warpsConfig.save(warpsFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save warps.yml");
        }
    }

    public void savePlayerData() {
        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save playerdata.yml");
        }
    }

    // Homes
    public void setHome(Player player, String name, Location location) {
        String path = player.getUniqueId().toString() + "." + name;
        homesConfig.set(path + ".world", location.getWorld().getName());
        homesConfig.set(path + ".x", location.getX());
        homesConfig.set(path + ".y", location.getY());
        homesConfig.set(path + ".z", location.getZ());
        homesConfig.set(path + ".yaw", location.getYaw());
        homesConfig.set(path + ".pitch", location.getPitch());
        saveHomes();
    }

    public Location getHome(Player player, String name) {
        String path = player.getUniqueId().toString() + "." + name;
        if (!homesConfig.contains(path)) return null;
        String world = homesConfig.getString(path + ".world");
        double x = homesConfig.getDouble(path + ".x");
        double y = homesConfig.getDouble(path + ".y");
        double z = homesConfig.getDouble(path + ".z");
        float yaw = (float) homesConfig.getDouble(path + ".yaw");
        float pitch = (float) homesConfig.getDouble(path + ".pitch");
        return new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
    }

    public Map<String, Location> getHomes(Player player) {
        Map<String, Location> homes = new HashMap<>();
        String uuid = player.getUniqueId().toString();
        if (homesConfig.contains(uuid)) {
            for (String key : homesConfig.getConfigurationSection(uuid).getKeys(false)) {
                homes.put(key, getHome(player, key));
            }
        }
        return homes;
    }

    public Set<String> getHomeNames(Player player) {
        String uuid = player.getUniqueId().toString();
        if (!homesConfig.contains(uuid)) {
            return Collections.emptySet();
        }

        if (homesConfig.getConfigurationSection(uuid) == null) {
            return Collections.emptySet();
        }

        return new HashSet<>(homesConfig.getConfigurationSection(uuid).getKeys(false));
    }

    public boolean deleteHome(Player player, String name) {
        String path = player.getUniqueId().toString() + "." + name;
        if (!homesConfig.contains(path)) {
            return false;
        }

        homesConfig.set(path, null);
        saveHomes();
        return true;
    }

    // Warps
    public void setWarp(String name, Location location) {
    String path = name.toLowerCase();
        warpsConfig.set(path + ".world", location.getWorld().getName());
        warpsConfig.set(path + ".x", location.getX());
        warpsConfig.set(path + ".y", location.getY());
        warpsConfig.set(path + ".z", location.getZ());
        warpsConfig.set(path + ".yaw", location.getYaw());
        warpsConfig.set(path + ".pitch", location.getPitch());
        saveWarps();
    }

    public Location getWarp(String name) {
        String path = name.toLowerCase();
        if (!warpsConfig.contains(path)) return null;
        String world = warpsConfig.getString(path + ".world");
        double x = warpsConfig.getDouble(path + ".x");
        double y = warpsConfig.getDouble(path + ".y");
        double z = warpsConfig.getDouble(path + ".z");
        float yaw = (float) warpsConfig.getDouble(path + ".yaw");
        float pitch = (float) warpsConfig.getDouble(path + ".pitch");
        return new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
    }

    public Set<String> getWarpNames() {
        if (warpsConfig.getKeys(false) == null || warpsConfig.getKeys(false).isEmpty()) {
            return Collections.emptySet();
        }

        return new HashSet<>(warpsConfig.getKeys(false));
    }

    public void deleteWarp(String name) {
        String path = name.toLowerCase();
        if (!warpsConfig.contains(path)) {
        return;
    }

    warpsConfig.set(path, null);
    saveWarps();
}

    // Spawn
    public void setSpawn(Location location) {
        playerDataConfig.set("spawn.world", location.getWorld().getName());
        playerDataConfig.set("spawn.x", location.getX());
        playerDataConfig.set("spawn.y", location.getY());
        playerDataConfig.set("spawn.z", location.getZ());
        playerDataConfig.set("spawn.yaw", location.getYaw());
        playerDataConfig.set("spawn.pitch", location.getPitch());
        savePlayerData();
    }

    public Location getSpawn() {
        if (!playerDataConfig.contains("spawn")) return null;
        String world = playerDataConfig.getString("spawn.world");
        double x = playerDataConfig.getDouble("spawn.x");
        double y = playerDataConfig.getDouble("spawn.y");
        double z = playerDataConfig.getDouble("spawn.z");
        float yaw = (float) playerDataConfig.getDouble("spawn.yaw");
        float pitch = (float) playerDataConfig.getDouble("spawn.pitch");
        return new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
    }

    // Last death location
    public void setLastDeath(Player player, Location location) {
        setLastLocation(player, location);
    }

    public Location getLastDeath(Player player) {
        return getLastLocation(player);
    }

    // Last location (for /back command - tracks deaths and teleports)
    public void setLastLocation(Player player, Location location) {
        String path = player.getUniqueId().toString() + ".lastlocation";
        playerDataConfig.set(path + ".world", location.getWorld().getName());
        playerDataConfig.set(path + ".x", location.getX());
        playerDataConfig.set(path + ".y", location.getY());
        playerDataConfig.set(path + ".z", location.getZ());
        playerDataConfig.set(path + ".yaw", location.getYaw());
        playerDataConfig.set(path + ".pitch", location.getPitch());
        savePlayerData();
    }

    public Location getLastLocation(Player player) {
        String path = player.getUniqueId().toString() + ".lastlocation";
        if (!playerDataConfig.contains(path)) return null;
        String world = playerDataConfig.getString(path + ".world");
        double x = playerDataConfig.getDouble(path + ".x");
        double y = playerDataConfig.getDouble(path + ".y");
        double z = playerDataConfig.getDouble(path + ".z");
        float yaw = (float) playerDataConfig.getDouble(path + ".yaw");
        float pitch = (float) playerDataConfig.getDouble(path + ".pitch");
        return new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
    }

    // AFK status
    public void setAFK(Player player, boolean afk) {
        playerDataConfig.set(player.getUniqueId().toString() + ".afk", afk);
        savePlayerData();
    }

    public boolean isAFK(Player player) {
        return playerDataConfig.getBoolean(player.getUniqueId().toString() + ".afk", false);
    }

    // God mode
    public void setGodMode(Player player, boolean god) {
        playerDataConfig.set(player.getUniqueId().toString() + ".god", god);
        savePlayerData();
    }

    public boolean isGodMode(Player player) {
        return playerDataConfig.getBoolean(player.getUniqueId().toString() + ".god", false);
    }

    // Frozen
    public void setFrozen(Player player, boolean frozen) {
        playerDataConfig.set(player.getUniqueId().toString() + ".frozen", frozen);
        savePlayerData();
    }

    public boolean isFrozen(Player player) {
        return playerDataConfig.getBoolean(player.getUniqueId().toString() + ".frozen", false);
    }

    // Muted
    public void setMuted(Player player, boolean muted) {
        playerDataConfig.set(player.getUniqueId().toString() + ".muted", muted);
        savePlayerData();
    }

    public boolean isMuted(Player player) {
        return playerDataConfig.getBoolean(player.getUniqueId().toString() + ".muted", false);
    }

    // Daily reward last claim
    public void setLastDailyClaim(Player player, long time) {
        playerDataConfig.set(player.getUniqueId().toString() + ".lastdaily", time);
        savePlayerData();
    }

    public long getLastDailyClaim(Player player) {
        return playerDataConfig.getLong(player.getUniqueId().toString() + ".lastdaily", 0);
    }

    // Balance
    public double getBalance(Player player) {
        return getBalance(player.getUniqueId());
    }

    public double getBalance(java.util.UUID uuid) {
        return playerDataConfig.getDouble(uuid.toString() + ".balance", plugin.getConfigManager().getEconomyConfig().getDouble("balance.starting-amount", 0.0));
    }

    public void setBalance(Player player, double amount) {
        setBalance(player.getUniqueId(), amount);
    }

    public void setBalance(java.util.UUID uuid, double amount) {
        playerDataConfig.set(uuid.toString() + ".balance", amount);
        savePlayerData();
    }

    public void addBalance(Player player, double amount) {
        addBalance(player.getUniqueId(), amount);
    }

    public void addBalance(java.util.UUID uuid, double amount) {
        double current = getBalance(uuid);
        setBalance(uuid, current + amount);
    }

    public boolean withdrawBalance(Player player, double amount) {
        return withdrawBalance(player.getUniqueId(), amount);
    }

    public boolean withdrawBalance(java.util.UUID uuid, double amount) {
        double current = getBalance(uuid);
        if (current >= amount) {
            setBalance(uuid, current - amount);
            return true;
        }
        return false;
    }
}
