package com.andrei1058.bedwars.arena.tasks;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class InvisFootstepsTask implements Runnable {
    private Player player;
    private BukkitTask task;

    private Location lastStep;

    public InvisFootstepsTask(Player player) {
        this.player = player;
        task = Bukkit.getScheduler().runTaskTimer(BedWars.plugin, this, 0, 4);
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void run() {
        System.out.println(player.getName() + " is invis");
        if(player == null || !player.isOnline()){
            task.cancel();
            return;
        }
        if (!((Entity) player).isOnGround()) return;

        boolean isInvis = false;
        for(PotionEffect pe : player.getActivePotionEffects()){
            if(pe.getType().toString().contains("INVISIBILITY")) isInvis = true;
        }
        if(!isInvis) task.cancel();

        if(lastStep != null && lastStep.distance(player.getLocation()) < 0.25) return;

        player.getWorld().playEffect(player.getLocation().add(new Vector(0, 0.01, 0)), Effect.FOOTSTEP, 200);
    }

    public void cancel() {
        task.cancel();
    }
}
