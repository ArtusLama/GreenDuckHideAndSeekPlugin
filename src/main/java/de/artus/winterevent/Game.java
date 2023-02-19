package de.artus.winterevent;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Game {


    public static boolean gameIsRunning = false;

    public static List<Player> hider = new ArrayList<>();
    public static int totalHiderCount = 0;
    public static List<Player> seeker = new ArrayList<>();

    public static void startGameLoop(){
        new BukkitRunnable() {

            int counter = 10;
            @Override
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) Main.sendMsg(player, "Spiel startet in &c" + counter + "&f!");
                if (counter <= 3) {
                    for (Player player : Bukkit.getOnlinePlayers()) player.sendTitle(ChatColor.RED + "" + counter, "", 5, 10, 5);
                }

                counter--;
                if (counter <= 0){
                    startHidingLoop();
                    cancel();
                }

            }
        }.runTaskTimer(Main.plugin, 20, 20);
    }
    public static void startHidingLoop(){
        gameIsRunning = true;

        totalHiderCount = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            boolean isHider = player.teamDisplayName().toString().contains("Hider");
            boolean isSeeker = player.teamDisplayName().toString().contains("Seeker");
            if (isSeeker) seeker.add(player);
            if (isHider){
                hider.add(player);
                totalHiderCount++;
                player.teleport(new Location(player.getWorld(), -1488, 35, 854, 45, 0));
                player.setGameMode(GameMode.ADVENTURE);
            }
        }

        for (Player player : seeker) {
            for (Player hidingPlayer : hider) {
                player.hidePlayer(hidingPlayer);
            }
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team modify Hider nametagVisibility hideForOtherTeams");

        new BukkitRunnable() {

            int counter = 40;
            @Override
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "Zeit übrig zum verstecken: " + ChatColor.RED + counter + ChatColor.GREEN + "!"));

                counter--;
                if (counter <= 0){
                    for (Player player : Bukkit.getOnlinePlayers()) player.sendTitle(ChatColor.GREEN + "LOOOS!", "", 5, 20, 5);

                    for (Player player : seeker) {
                        for (Player hidingPlayer : hider) {
                            player.showPlayer(hidingPlayer);
                        }
                        player.setGameMode(GameMode.ADVENTURE);
                        player.teleport(new Location(player.getWorld(), -1488, 35, 854, 45, 0));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 999999, 255).withParticles(false));
                    }

                    startGame();
                    cancel();
                }

            }
        }.runTaskTimer(Main.plugin, 20, 20);
    }

    public static void startGame(){

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (seeker.contains(player)) player.getInventory().setItem(1, new ItemStack(Material.RECOVERY_COMPASS, 10));
        }

        gameIsRunning = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!gameIsRunning) cancel();

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED +  "" + hider.size() + "/" + totalHiderCount));
                    if (seeker.contains(player)) player.getInventory().setItem(0, new ItemStack(Material.SNOWBALL, 16));
                }
            }
        }.runTaskTimer(Main.plugin, 10, 10);
    }


    public static void stopGame(){
        gameIsRunning = false;
        hider.clear();
        seeker.clear();
        totalHiderCount = 0;
        for (Player player : Bukkit.getOnlinePlayers()){
            player.setGameMode(GameMode.ADVENTURE);
            player.teleport(new Location(player.getWorld(), -1490, 71, 881, -90, 0));
            Main.sendMsg(player, "SPIEL ZUENDE!!!");
            player.getInventory().clear();
            player.removePotionEffect(PotionEffectType.GLOWING);
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team modify Hider nametagVisibility always");
    }

}
