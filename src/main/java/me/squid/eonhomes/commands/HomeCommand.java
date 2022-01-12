package me.squid.eonhomes.commands;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.event.QueryHomeEvent;
import me.squid.eonhomes.managers.HomeManager;
import me.squid.eonhomes.managers.Home;
import me.squid.eonhomes.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.concurrent.CompletableFuture;

public class HomeCommand implements CommandExecutor, Listener {

    EonHomes plugin;
    HomeManager homeManager;

    public HomeCommand(EonHomes plugin, HomeManager homeManager) {
        this.plugin = plugin;
        this.homeManager = homeManager;
        plugin.getCommand("home").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 1) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin,
                        () -> Bukkit.getPluginManager().callEvent(new QueryHomeEvent(p.getUniqueId(), p.getUniqueId(), args[0])));
            } else if (args.length == 2 && p.hasPermission("eoncommands.home.bypass")) {
                OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(args[0]);
                if (target != null) {
                    Bukkit.getScheduler().runTaskAsynchronously(plugin,
                            () -> Bukkit.getPluginManager().callEvent(
                                    new QueryHomeEvent(target.getUniqueId(), p.getUniqueId(), args[1])));
                } else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Player not found"));
            } else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Usage: /home <name>"));
        }
        return true;
    }

    @EventHandler
    public void onQueryHomeEvent(QueryHomeEvent e) {
        boolean homeExists = homeManager.homeExists(e.getTarget(), e.getName()).join();
        Home home = homeExists ? homeManager.getHome(e.getTarget(), e.getName()).join() : null;
        Player p = Bukkit.getPlayer(e.getSender());
        if (home != null) {
            if (p != null && p.isOnline()) {
                if (p.isOp()) {
                    Bukkit.getScheduler().runTask(plugin, () -> p.teleportAsync(home.getLocation()));
                    sendMessage(p, "Successfully teleported to " + home.getName());
                } else {
                    sendMessage(p, "Teleporting in 3 seconds...");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        p.teleportAsync(home.getLocation());
                        p.sendMessage(Utils.chat(EonHomes.prefix + "&7Successfully teleported to " + home.getName()));
                    }, 60L);
                }
            }
        } else if (p != null && p.isOnline()) sendMessage(p, "Home is invalid");
    }

    private void sendMessage(Player player, String message) {
        Bukkit.getScheduler().runTask(plugin,
                () -> player.sendMessage(Utils.chat(EonHomes.prefix + "&7" + message)));
    }
}
