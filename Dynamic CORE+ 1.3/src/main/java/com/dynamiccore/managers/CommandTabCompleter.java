package com.dynamiccore.managers;

import com.dynamiccore.DynamicCOREPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class CommandTabCompleter implements TabCompleter {

    private final DynamicCOREPlus plugin;

    public CommandTabCompleter(DynamicCOREPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String name = command.getName().toLowerCase(Locale.ROOT);

        if (args.length == 0) {
            return Collections.emptyList();
        }

        switch (name) {
            case "home":
            case "delhome":
                if (sender instanceof Player && args.length == 1) {
                    Set<String> homes = plugin.getPlayerDataManager().getHomeNames((Player) sender);
                    return filterByPrefix(homes, args[0]);
                }
                return Collections.emptyList();
            case "warp":
            case "delwarp":
                if (args.length == 1) {
                    Set<String> warps = plugin.getPlayerDataManager().getWarpNames();
                    return filterByPrefix(warps, args[0]);
                }
                return Collections.emptyList();
            case "eco":
                if (args.length == 1) {
                    return filterByPrefix(Arrays.asList("give", "take", "set", "reset"), args[0]);
                }
                if (args.length == 2) {
                    return filterByPrefix(getOnlinePlayerNames(sender, false), args[1]);
                }
                return Collections.emptyList();
            case "leaderboard":
                if (args.length == 1) {
                    return filterByPrefix(Collections.singletonList("economy"), args[0]);
                }
                return Collections.emptyList();
            case "dcore":
                if (args.length == 1) {
                    return filterByPrefix(Collections.singletonList("reload"), args[0]);
                }
                return Collections.emptyList();
            case "enchant":
                if (args.length == 1) {
                    return filterByPrefix(getEnchantmentNames(), args[0]);
                }
                return Collections.emptyList();
            case "tpa":
            case "tpahere":
            case "pay":
            case "mute":
            case "ban":
            case "kick":
            case "freeze":
            case "invsee":
            case "stats":
            case "bal":
            case "god":
            case "clear":
                if (args.length == 1) {
                    boolean excludeSelf = name.equals("tpa") || name.equals("tpahere") || name.equals("pay");
                    return filterByPrefix(getOnlinePlayerNames(sender, excludeSelf), args[0]);
                }
                return Collections.emptyList();
            default:
                return Collections.emptyList();
        }
    }

    private List<String> getOnlinePlayerNames(CommandSender sender, boolean excludeSelf) {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        List<String> names = new ArrayList<>(players.size());
        for (Player player : players) {
            if (excludeSelf && sender instanceof Player) {
                if (((Player) sender).getUniqueId().equals(player.getUniqueId())) {
                    continue;
                }
            }
            names.add(player.getName());
        }
        return names;
    }

    private List<String> getEnchantmentNames() {
        List<String> names = new ArrayList<>();
        for (Enchantment enchantment : Enchantment.values()) {
            if (enchantment.getKey() != null) {
                names.add(enchantment.getKey().getKey());
            }
        }
        return names;
    }

    private List<String> filterByPrefix(Collection<String> options, String prefix) {
        if (options == null || options.isEmpty()) {
            return Collections.emptyList();
        }

        String lowerPrefix = prefix == null ? "" : prefix.toLowerCase(Locale.ROOT);
        List<String> results = new ArrayList<>();
        for (String option : options) {
            if (option == null) {
                continue;
            }
            if (option.toLowerCase(Locale.ROOT).startsWith(lowerPrefix)) {
                results.add(option);
            }
        }
        Collections.sort(results);
        return results;
    }
}
