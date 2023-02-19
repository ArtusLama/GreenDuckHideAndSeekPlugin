package de.artus.winterevent;

import de.artus.winterevent.commands.PlayersCommand;
import de.artus.winterevent.commands.ShowPlayersCommand;
import de.artus.winterevent.commands.StartStopCommand;
import de.artus.winterevent.listeners.BarrierTp;
import de.artus.winterevent.listeners.HitPlayer;
import de.artus.winterevent.listeners.Interact;
import de.artus.winterevent.listeners.JoinQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getCommand("game").setExecutor(new StartStopCommand());
        getCommand("showplayers").setExecutor(new ShowPlayersCommand());
        getCommand("players").setExecutor(new PlayersCommand());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinQuitListener(), this);
        pluginManager.registerEvents(new BarrierTp(), this);
        pluginManager.registerEvents(new HitPlayer(), this);
        pluginManager.registerEvents(new Interact(), this);


    }

    @Override
    public void onDisable() {

    }

    public static void sendMsg(Player player, String msg){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cW&fI&cN&fT&cE&fR&c - &fE&cV&fE&cN&fT&8] -> &f" + msg));
    }
    public static String getRedWhiteColorMsg(String msg) {
        StringBuilder out = new StringBuilder();
        String colorCode;
        for (int letter = 0; letter < msg.length(); letter++) {
            if (letter % 2 == 0) colorCode = "&c";
            else colorCode = "&f";
            out.append(colorCode).append(msg.charAt(letter));
        }
        return out.toString();
    }
}
