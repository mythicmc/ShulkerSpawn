package org.mythicmc.shulkerspawn;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {
    ShulkerSpawn plugin;

    public CommandHandler(ShulkerSpawn plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("shulkerspawn.admin")) {
            sender.sendMessage(ChatColor.RED + "You do not have the permission to perform this action");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8---------------[ &3" + plugin.getDescription().getName() + " &fv" + plugin.getDescription().getVersion() + " &8]---------------"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &7- Current Shulker SpawnRate: &f" + plugin.spawnChance + "%"));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.readConfig();
            sender.sendMessage(ChatColor.GRAY + "Reloaded plugin configuration");
            return true;
        } else if (args[0].equalsIgnoreCase("set")) {
            if (args.length != 3) {
                sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.WHITE + "/shulkerspawn set <param> <value>");
                return true;
            }
            if (args[1].equalsIgnoreCase("SpawnChance")) {
                int chance;
                try {
                    chance = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Value must be an integer (0-100)");
                    return true;
                }
                if (chance >= 0 && chance <= 100) {
                    plugin.getConfig().set("SpawnChance", chance);
                    plugin.saveConfig();
                    plugin.readConfig();
                    sender.sendMessage(ChatColor.GRAY + " done :>");
                    return true;
                }
                sender.sendMessage(ChatColor.GRAY + "Value of SpawnChance must be an Integer between 0 and 100");
                return true;
            }
            sender.sendMessage(ChatColor.GRAY + "Available params = 'SpawnChance'");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GRAY + "/shulkerspawn reload/set");
        return true;
    }
}
