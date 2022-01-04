package me.squid.eonhomes.utils;

import me.squid.eonhomes.EonHomes;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PublicServiceAnnouncement implements Listener {

    EonHomes plugin;

    public PublicServiceAnnouncement(EonHomes plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        String message = "Welcome back! I rewrote the entire homes plugin, so you do not have the homes you had before saved. Don't worry.";
        String message2 = "This is expected and I have all the homes saved from before. When I am on, I can tp you as needed. Thank you.";
        e.getPlayer().sendMessage(Utils.chat(EonHomes.prefix + "&7" + message));
        e.getPlayer().sendMessage(Utils.chat(EonHomes.prefix + "&7" + message2));
    }
}
