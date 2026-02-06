package com.dynamiccore.managers;

import com.dynamiccore.DynamicCOREPlus;
import com.dynamiccore.commands.*;
import org.bukkit.command.CommandExecutor;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final DynamicCOREPlus plugin;
    private final Map<String, CommandExecutor> commands = new HashMap<>();

    public CommandManager(DynamicCOREPlus plugin) {
        this.plugin = plugin;
        registerCommands();
    }

    private void registerCommands() {
        commands.put("home", new HomeCommand(plugin));
        commands.put("sethome", new SetHomeCommand(plugin));
        commands.put("delhome", new DelHomeCommand(plugin));
        commands.put("tpa", new TPACommand(plugin));
        commands.put("tpahere", new TPACommand(plugin));
        commands.put("tpaccept", new TPAAcceptCommand(plugin));
        commands.put("tpdeny", new TPADenyCommand(plugin));
        commands.put("warp", new WarpCommand(plugin));
        commands.put("setwarp", new SetWarpCommand(plugin));
        commands.put("delwarp", new DelWarpCommand(plugin));
        commands.put("spawn", new SpawnCommand(plugin));
        commands.put("setspawn", new SetSpawnCommand(plugin));
        commands.put("back", new BackCommand(plugin));
        commands.put("rtp", new RTPCommand(plugin));
        commands.put("afk", new AFKCommand(plugin));
        commands.put("bal", new BalanceCommand(plugin));
        commands.put("pay", new PayCommand(plugin));
        commands.put("daily", new DailyCommand(plugin));
        commands.put("eco", new EcoCommand(plugin));
        commands.put("mute", new MuteCommand(plugin));
        commands.put("ban", new BanCommand(plugin));
        commands.put("kick", new KickCommand(plugin));
        commands.put("freeze", new FreezeCommand(plugin));
        commands.put("god", new GodCommand(plugin));
        commands.put("clear", new ClearCommand(plugin));
        commands.put("broadcast", new BroadcastCommand(plugin));
        commands.put("staffchat", new StaffChatCommand(plugin));
        commands.put("repair", new RepairCommand(plugin));
        commands.put("enchant", new EnchantCommand(plugin));
        commands.put("rename", new RenameCommand(plugin));
        commands.put("stats", new StatsCommand(plugin));
        commands.put("leaderboard", new LeaderboardCommand(plugin));
        commands.put("invsee", new InvSeeCommand(plugin));
        commands.put("dcore", new ReloadCommand(plugin, "dynamiccore+.moderation.reload"));

        CommandTabCompleter tabCompleter = new CommandTabCompleter(plugin);

        for (Map.Entry<String, CommandExecutor> entry : commands.entrySet()) {
            plugin.getCommand(entry.getKey()).setExecutor(entry.getValue());
            plugin.getCommand(entry.getKey()).setTabCompleter(tabCompleter);
        }
    }
}
