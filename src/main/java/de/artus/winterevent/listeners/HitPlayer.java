package de.artus.winterevent.listeners;

import de.artus.winterevent.Game;
import de.artus.winterevent.Main;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class HitPlayer implements Listener {


    @EventHandler
    public void onPlayerHit(ProjectileHitEvent e) {

        if (e.getEntity() instanceof Snowball snowball) {
            if (e.getHitEntity() instanceof Player target) {
                target.damage(7, (Player) snowball.getShooter());
            }
        }
    }

    @EventHandler
    public void playerHitEvent(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player target) {
            if (!Game.gameIsRunning) e.setCancelled(true);
            boolean targetIsSeeker = target.teamDisplayName().toString().contains("Seeker");
            if (targetIsSeeker) e.setCancelled(true);

        } else e.setCancelled(true);
        if (e.getDamager() instanceof Player attacker) if (attacker.getGameMode() == GameMode.CREATIVE) e.setCancelled(false);
    }
    @EventHandler
    public void onMinectartDestroy(VehicleDestroyEvent e) {
        if (e.getAttacker() instanceof Player attacker) {
            if (attacker.getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
        }
    }
    

    @EventHandler
    public void onFound(PlayerDeathEvent e) {
        if (!Game.gameIsRunning) return;
        Game.hider.remove(e.getPlayer());
        e.setDeathMessage(ChatColor.RED + "" + e.getPlayer().getName() + ChatColor.WHITE + " ist raus! " + ChatColor.RED + Game.hider.size() + "/" + Game.totalHiderCount + ChatColor.WHITE + " übrig!");

        new BukkitRunnable() {
            @Override
            public void run() {
                e.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
        }.runTaskLater(Main.plugin, 10);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Game.hider.isEmpty()) Game.stopGame();
            }
        }.runTaskLater(Main.plugin, 20);

    }



}
