package de.artus.winterevent.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Interact implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if (e.getPlayer().getGameMode() == GameMode.ADVENTURE) {
            if (e.getItem() != null) {
                if (e.getItem().getType() == Material.RECOVERY_COMPASS) {
                    e.getItem().setAmount(e.getItem().getAmount() - 1);
                    e.getPlayer().performCommand("showplayers");
                    e.setCancelled(true);
                    return;
                }
                e.setCancelled(true);
                if (e.getItem().getType() == Material.SNOWBALL) {
                    if (!e.hasBlock()) e.setCancelled(false);
                }
            } else e.setCancelled(true);
        }
    }


    @EventHandler (priority = EventPriority.HIGH)
    public void onInteractAtEntity(PlayerInteractAtEntityEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.ADVENTURE) {
            if (e.getRightClicked().getType() == EntityType.MINECART) e.setCancelled(false);
            if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) e.setCancelled(true);
        }
    }



}
