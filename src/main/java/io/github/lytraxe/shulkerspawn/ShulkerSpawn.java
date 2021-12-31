package io.github.lytraxe.shulkerspawn;

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
        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.hasPermission("shulkerspawn.admin")) {
            readConfig();
            sender.sendMessage(ChatColor.GRAY + "Reloaded plugin configuration");
            return true;
        } else if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8---------------[ &3" + getDescription().getName() + " &fv" + getDescription().getVersion() + " &8]---------------"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &7- Current Shulker SpawnRate: &r" + spawnChance + "%"));
            return true;
        }
        return false;
    }
}