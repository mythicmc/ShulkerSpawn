package org.mythicmc.shulkerspawn;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class SpawnListener implements Listener {
    ShulkerSpawn plugin;

    public SpawnListener(ShulkerSpawn plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Enderman && event.getLocation().getWorld().getEnvironment() == World.Environment.THE_END
                && (event.getLocation().getBlock().getBiome() == Biome.END_HIGHLANDS || event.getLocation().getBlock().getBiome() == Biome.END_MIDLANDS)) {

            if (new Random().nextInt(101) <= plugin.spawnChance) {
                Entity e = event.getEntity();
                Location loc = e.getLocation();
                World world = loc.getWorld();

                if (loc.getBlock().getType().toString().contains("PURPUR") || loc.subtract(0,1,0).getBlock().getType().toString().contains("PURPUR")) {
                    event.setCancelled(true);
                    world.spawn(loc, Shulker.class);
                }
            }
        }
    }
}
