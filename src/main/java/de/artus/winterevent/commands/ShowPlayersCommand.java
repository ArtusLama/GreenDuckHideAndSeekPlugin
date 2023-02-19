package de.artus.winterevent.commands;

import de.artus.winterevent.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class ShowPlayersCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            boolean isSeeker = player.teamDisplayName().toString().contains("Seeker");
            if (isSeeker) {
                int duration = 3;
                if (args.length == 1) duration = Integer.parseInt(args[0]);
                for (Player hider : Game.hider) {
                    hider.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, duration * 20, 255).withParticles(false));
                }
            }
        }

        return false;
    }
}
