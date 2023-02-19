package de.artus.winterevent.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class BarrierTp implements Listener {

    @EventHandler
    public void onStepOnBarriers(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (e.getTo().clone().subtract(0, 1, 0).getBlock().getType() == Material.BARRIER && player.getGameMode() != GameMode.SPECTATOR) {
            player.teleport(new Location(player.getWorld(), -1490, 71, 881, -90, 0));
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) e.setCancelled(true);
        }
    }




}
