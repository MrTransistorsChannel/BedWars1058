package com.andrei1058.bedwars.arena.tasks;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class SpectatorInventoryViewTask implements Runnable {

    private final Player spectator, target;
    private BukkitTask task;

    public SpectatorInventoryViewTask(Player spectator, Player target) {
        this.spectator = spectator;
        this.target = target;
        this.task = Bukkit.getScheduler().runTaskTimer(BedWars.plugin, this,0,5L);
    }

    @Override
    public void run() {
        if(spectator.getSpectatorTarget() != target){
            task.cancel();
            // Give spectator items when leaving FPV
            IArena ar = Arena.getArenaByPlayer(spectator);
            ItemStack[] emptyArmor = {
                    new ItemStack(Material.AIR, 1),
                    new ItemStack(Material.AIR, 1),
                    new ItemStack(Material.AIR, 1),
                    new ItemStack(Material.AIR, 1),
            };
            spectator.getInventory().setArmorContents(emptyArmor);
            ar.sendSpectatorCommandItems(spectator);
            return;
        }
        spectator.getInventory().setArmorContents(target.getInventory().getArmorContents());
        spectator.getInventory().setContents(target.getInventory().getContents());
    }


}
