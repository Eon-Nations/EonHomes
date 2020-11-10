package me.squid.eonhomes.listeners;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.files.Homes;
import me.squid.eonhomes.utils.HomeManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinLeaveListener implements Listener {

    EonHomes plugin;

    public JoinLeaveListener(EonHomes plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Homes homes = new Homes();
        if (homes.getConfig().getConfigurationSection("Homes." + e.getPlayer().getUniqueId().toString()) == null) {
            homes.getConfig().createSection("Homes." + e.getPlayer().getUniqueId());
        }
    }
}
