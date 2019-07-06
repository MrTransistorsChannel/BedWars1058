package com.andrei1058.bedwars.arena.mapreset;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.mapreset.internal.WorldOperations.WorldOperator;
import com.andrei1058.bedwars.arena.mapreset.internal.WorldOperations.WorldRestorer;
import com.andrei1058.bedwars.arena.mapreset.internal.WorldOperations.WorldZipper;
import com.andrei1058.bedwars.configuration.ConfigPath;
import org.bukkit.*;

import java.io.File;

public class MapManager {

    private Arena arena;
    private String name;

    public static File backupFolder = new File(Main.plugin.getDataFolder() + "/Cache");

    /**
     * Create a new map manager for an arena.
     */
    public MapManager(Arena arena, String name) {
        this.arena = arena;
        this.name = name;
        //isLevelWorld();
    }

    /**
     * Load world.
     */
    public void loadWorld() {
        isLevelWorld();
        Bukkit.getScheduler().runTask(Main.plugin, () -> {
            World w = Bukkit.getServer().createWorld(new WorldCreator(name));

            w.setKeepSpawnInMemory(false);
            w.setAutoSave(false);
            if (arena != null) arena.init(w);
        });
    }

    /**
     * Unload world.
     */
    public void unloadWorld() {
        //if (isLevelWorld()) return;
        Bukkit.unloadWorld(Bukkit.getWorld(name), false);
    }

    /**
     * Restore arena world.
     */
    public void restoreWorld(String name, Arena arena) {
        //if (isLevelWorld()) return;
        WorldOperator worldOperator;

        worldOperator = new WorldRestorer(name, arena);
        worldOperator.execute();
    }

    /**
     * Backup arena world.
     */
    public void backupWorld(boolean replace) {
        //if (isLevelWorld()) return;
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            WorldOperator worldOperator = new WorldZipper(name, replace);
            worldOperator.execute();
        });
    }

    /**
     * Remove lobby.
     */
    public void removeLobby() {
        Location loc1 = arena.getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS1),
                loc2 = arena.getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS2);
        if (loc1 == null || loc2 == null) return;
        Bukkit.getScheduler().runTask(Main.plugin, () -> {
            int minX, minY, minZ;
            int maxX, maxY, maxZ;
            minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
            maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
            minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
            maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
            minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
            maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

            for (int x = minX; x < maxX; x++) {
                for (int y = minY; y < maxY; y++) {
                    for (int z = minZ; z < maxZ; z++) {
                        loc1.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
                    }
                }
            }
        });
    }

    /**
     * Get world name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get arena.
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Make it return false if your system is compatible wit level-name map.
     */
    public void isLevelWorld() {

        if (Bukkit.getWorlds().isEmpty()) return;
        if (Bukkit.getWorlds().get(0).getName().equalsIgnoreCase(name)) {
            Main.plugin.getLogger().severe("-------------------");
            if (Main.getServerType() == ServerType.BUNGEE) {
                Main.plugin.getLogger().severe("PLEASE CONSIDER ADDING A VOID MAP AS level-name IN server.properties");
                return;
            }
            Main.plugin.getLogger().severe("ARENA WORLDS MUSTN'T BE USED AS MAIN WORLD AT level-name IN server.properties");
        }
    }
}