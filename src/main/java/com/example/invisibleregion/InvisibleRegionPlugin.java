package com.example.invisibleregion;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

import net.raidstone.wgevents.events.RegionEnteredEvent;
import net.raidstone.wgevents.events.RegionLeftEvent;

public class InvisibleRegionPlugin extends JavaPlugin implements Listener {

    private List<String> targetRegions;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        targetRegions = config.getStringList("invisible-regions");

        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("InvisibleRegionPlugin aktif!");
    }

    @EventHandler
    public void onRegionEnter(RegionEnteredEvent event) {
        Player player = event.getPlayer();
        if (targetRegions.contains(event.getRegionName().toLowerCase())) {
            for (Player other : Bukkit.getOnlinePlayers()) {
                if (!other.equals(player)) {
                    other.hidePlayer(this, player);
                }
            }
        }
    }

    @EventHandler
    public void onRegionLeave(RegionLeftEvent event) {
        Player player = event.getPlayer();
        if (targetRegions.contains(event.getRegionName().toLowerCase())) {
            for (Player other : Bukkit.getOnlinePlayers()) {
                if (!other.equals(player)) {
                    other.showPlayer(this, player);
                }
            }
        }
    }
}
