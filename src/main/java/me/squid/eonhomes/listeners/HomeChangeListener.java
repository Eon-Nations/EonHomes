package me.squid.eonhomes.listeners;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.utils.Group;
import me.squid.eonhomes.Home;
import me.squid.eonhomes.event.NewHomeEvent;
import me.squid.eonhomes.event.RemoveHomeEvent;
import me.squid.eonhomes.utils.HomeManager;
import me.squid.eonhomes.utils.Utils;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.List;

public class HomeChangeListener implements Listener {

    EonHomes plugin;
    List<Home> homes;

    public HomeChangeListener(EonHomes plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onHomeAdd(NewHomeEvent e) {
        if (canMakeNewHome(e.getPlayer())) {
            Home home = new Home(e.getLocation().serialize(), e.getPlayer().getUniqueId(), e.getName());
            HomeManager.addHome(home);
            Bukkit.getScheduler().runTask(plugin, () -> e.getPlayer().sendMessage(Utils.chat(EonHomes.prefix + "&7Home set")));
        } else e.getPlayer().sendMessage(Utils.chat(EonHomes.prefix + "&7Not enough homes available. Rankup for more."));
    }

    private boolean canMakeNewHome(Player p) {
        if (!HomeManager.isInMap(p)) HomeManager.addPlayerToMap(p);

        if (p.hasPermission("eonhomes.amount.unlimited")) {
            return true;
        } else {
            int currentAmount = HomeManager.getHomes(p).size();
            int maxAmount = getMaxAmountOfHomes(p);
            return currentAmount < maxAmount;
        }
    }

    @EventHandler
    public void onHomeRemove(RemoveHomeEvent e) {
        homes = HomeManager.getHomes(e.getPlayer());

        if (homes != null) {
            for (Home home : homes) {
                if (home.getName().equalsIgnoreCase(e.getName())){
                    HomeManager.removeHome(home);
                    Bukkit.getScheduler().runTask(plugin, () -> e.getPlayer().sendMessage(Utils.chat(EonHomes.prefix + "&7Home removed")));
                    return;
                }
            }
            Bukkit.getScheduler().runTask(plugin, () -> e.getPlayer().sendMessage(Utils.chat(EonHomes.prefix + "&7Home not found.")));
        } else Bukkit.getScheduler().runTask(plugin, () -> e.getPlayer().sendMessage(Utils.chat(EonHomes.prefix + "&7No homes found to remove")));
    }

    private int getMaxAmountOfHomes(Player p) {
        LuckPerms api = EonHomes.api;
        HashMap<Group, Integer> homeValues = Group.getHomeValues();

        String pgroup = api.getUserManager().getUser(p.getUniqueId()).getPrimaryGroup();

        for (Group g : Group.values()) {
            Group group = Group.valueOf(pgroup.toUpperCase());
            if (group.equals(g)) return homeValues.get(g);
        }
        return 1;
    }
}
