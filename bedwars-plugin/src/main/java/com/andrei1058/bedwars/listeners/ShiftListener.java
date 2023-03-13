package com.andrei1058.bedwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class ShiftListener implements Listener {
    @EventHandler
    public void onPlayerShift(PlayerToggleSneakEvent e){
        if(e.getPlayer().getSpectatorTarget() == null) return;
        e.setCancelled(true);
    }
}
