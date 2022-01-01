package org.mythicmc.shulkerspawn;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class ShulkerSpawn extends JavaPlugin implements Listener {
    int spawnChance = 0;

    @Override
    public void onEnable() {
        readConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {}

    //-
    //- Original Author : https://github.com/JoelGodOfwar
    //- https://github.com/JoelGodOfwar/ShulkerRespawner/blob/master/1.13/src/com/github/joelgodofwar/sr/ShulkerRespawner.java
    //-

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Enderman && event.getLocation().getWorld().getEnvironment() == World.Environment.THE_END
                && (event.getLocation().getBlock().getBiome() == Biome.END_HIGHLANDS || event.getLocation().getBlock().getBiome() == Biome.END_MIDLANDS)) {
            if (theDecisionMaker()) {
                Entity e = event.getEntity();
                Location loc = e.getLocation();
                World world = loc.getWorld();
                if (loc.subtract(0,1,0).getBlock().getType().toString().contains("PURPUR") || loc.getBlock().getType().toString().contains("PURPUR")) {
                    event.setCancelled(true);
                    world.spawn(loc, Shulker.class);
                }
            }
        }
    }

    boolean theDecisionMaker() {
        Random r = new Random();
        return r.nextInt(101) <= spawnChance;
    }

    void readConfig() {
        saveDefaultConfig();
        reloadConfig();
        spawnChance = getConfig().getInt("SpawnChance");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("shulkerspawn.admin")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8---------------[ &3" + getDescription().getName() + " &fv" + getDescription().getVersion() + " &8]---------------"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &7- Current Shulker SpawnRate: &r" + spawnChance + "%"));
                return true;
            }
            //- Reload command
            if (args[0].equalsIgnoreCase("reload")) {
                readConfig();
                sender.sendMessage(ChatColor.GRAY + "Reloaded plugin configuration");
                return true;
            }
            //- set config command (really unnecessary but okay)
            else if (args[0].equalsIgnoreCase("set")) {
                if (args.length == 3) {
                    if (args[1].equalsIgnoreCase("SpawnChance")) {
                        int chance;
                        try {chance = Integer.parseInt(args[2]);} catch (NumberFormatException e) {sender.sendMessage(ChatColor.RED + "Value must be an integer (0-100)"); return true;}
                        if (chance >= 0 && chance <= 100) {
                            getConfig().set("SpawnChance", chance);
                            saveConfig();
                            readConfig();
                            sender.sendMessage(ChatColor.GRAY + " done :>");
                            return true;
                        }
                        sender.sendMessage(ChatColor.GRAY + "Value of SpawnChance must be an Integer between 0 and 100"); return true;
                    }
                    sender.sendMessage(ChatColor.GRAY + "Available params = 'SpawnChance'"); return true;
                }
                sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.WHITE + "/shulkerspawn set <param> <value>"); return true;
            }
            return false;
        }
        sender.sendMessage(ChatColor.RED + "You do not have the permission to perform this action");
        return true;
    }
}
