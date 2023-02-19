package de.artus.winterevent.commands;

import de.artus.winterevent.Game;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;


public class PlayersCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.isOp()) return false;

        if (args[1].equalsIgnoreCase("seeker")){
            Game.seeker.add(Bukkit.getPlayer(args[0]));
            Game.hider.remove(Bukkit.getPlayer(args[0]));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Seeker " + args[0]);
            Bukkit.getPlayer(args[0]).removePotionEffect(PotionEffectType.GLOWING);
        }
        if (args[1].equalsIgnoreCase("hider")){
            Game.hider.add(Bukkit.getPlayer(args[0]));
            Game.seeker.remove(Bukkit.getPlayer(args[0]));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Hider " + args[0]);
            Bukkit.getPlayer(args[0]).removePotionEffect(PotionEffectType.GLOWING);
        }

        return false;
    }
}
