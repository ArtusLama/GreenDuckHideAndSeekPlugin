package de.artus.winterevent.listeners;

import de.artus.winterevent.Game;
import de.artus.winterevent.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (Game.gameIsRunning) {
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
        String name = e.getPlayer().getName();
        String msg = "&c" + name + "&f ist dem Server beigetreten!";

        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        String name = e.getPlayer().getName();
        String msg = "&c" + name + "&f hat den Server verlassen!";
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', msg));

        boolean isHider = e.getPlayer().teamDisplayName().toString().contains("Hider");
        if (!isHider) return;

        e.getPlayer().removePotionEffect(PotionEffectType.GLOWING);
        e.getPlayer().setGameMode(GameMode.ADVENTURE);
        e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), -1490, 71, 881, -90, 0));
        e.getPlayer().getInventory().clear();

        if (Game.gameIsRunning) {
            Game.hider.remove(e.getPlayer());

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(ChatColor.RED + "" + e.getPlayer().getName() + ChatColor.WHITE + " ist raus! " + ChatColor.RED + Game.hider.size() + "/" + Game.totalHiderCount + ChatColor.WHITE + " übrig!");
                    }

                    if (Game.hider.isEmpty()) Game.stopGame();
                }
            }.runTaskLater(Main.plugin, 20);
        }
    }
}
