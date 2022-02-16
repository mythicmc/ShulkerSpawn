package org.mythicmc.shulkerspawn;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class ShulkerSpawn extends JavaPlugin {
    public int spawnChance = 0;

    @Override
    public void onEnable() {
        readConfig();
        getServer().getPluginManager().registerEvents(new SpawnListener(this), this);
        Objects.requireNonNull(getCommand("shulkerspawn")).setExecutor(new CommandHandler(this));
    }

    @Override
    public void onDisable() {}

    void readConfig() {
        saveDefaultConfig();
        reloadConfig();
        spawnChance = getConfig().getInt("SpawnChance");
    }
}
