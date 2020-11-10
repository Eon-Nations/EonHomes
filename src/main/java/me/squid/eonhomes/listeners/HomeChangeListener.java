package me.squid.eonhomes.listeners;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.Home;
import me.squid.eonhomes.event.NewHomeEvent;
import me.squid.eonhomes.event.RemoveHomeEvent;
import me.squid.eonhomes.files.Homes;
import me.squid.eonhomes.utils.HomeManager;
import me.squid.eonhomes.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class HomeChangeListener implements Listener {

    EonHomes plugin;
    Homes homes = new Homes();

    public HomeChangeListener(EonHomes plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onHomeAdd(NewHomeEvent e) {
        if (canMakeNewHome(e.getPlayer())) {
            Home home = new Home(e.getLocation(), e.getPlayer(), e.getName());
            HomeManager.addHome(home);
            Bukkit.getScheduler().runTask(plugin, () -> e.getPlayer().sendMessage(Utils.chat(EonHomes.prefix + "&7Home set")));
        } else e.getPlayer().sendMessage(Utils.chat(EonHomes.prefix + "&7Not enough homes available. Rankup for more"));
    }

    private boolean canMakeNewHome(Player p) {
        if (p.hasPermission("eonhomes.amount.unlimited")) return true;
        int amount = homes.getConfig().getInt("Homes." + p.getUniqueId().toString() + ".amount");
        return amount < getMaxAmountOfHomes(p);
    }

    @EventHandler
    public void onHomeRemove(RemoveHomeEvent e) {
        HomeManager.removeHome(e.getHome());
        Bukkit.getScheduler().runTask(plugin, () -> e.getPlayer().sendMessage(Utils.chat(EonHomes.prefix + "&7Home removed")));
    }

    private int getMaxAmountOfHomes(Player p) {
        for (int i = 1; i < 31; i++) {
            if (p.hasPermission("eonhomes.amount." + i)) return i;
        }
        return 3;
    }
}
