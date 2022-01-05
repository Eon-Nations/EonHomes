package me.squid.eonhomes.commands;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.event.NewHomeEvent;
import me.squid.eonhomes.managers.Home;
import me.squid.eonhomes.managers.HomeManager;
import me.squid.eonhomes.utils.Utils;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class SetHomeCommand implements CommandExecutor, Listener {

    EonHomes plugin;
    HomeManager homeManager;
    LuckPerms luckPerms;
    
    public SetHomeCommand(EonHomes plugin, HomeManager homeManager, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.homeManager = homeManager;
        this.luckPerms = luckPerms;
        plugin.getCommand("sethome").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player p) {
            // Just in case the location changes by the time the home is created.
            Location homeLocation = p.getLocation();
            if (args.length == 1) {
                if (args[0].length() > 15) {
                    p.sendMessage(Utils.chat(EonHomes.prefix + "&7The maximum length for a name is 15 characters. Please try again."));
                    return true;
                }
                Bukkit.getScheduler().runTaskAsynchronously(plugin,
                        () -> Bukkit.getPluginManager().callEvent(new NewHomeEvent(p, homeLocation, args[0])));
            } else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Usage: /sethome <home>"));
        }
        return true;
    }

    @EventHandler
    public void onHomeCreateEvent(NewHomeEvent e) {
        Player p = e.getPlayer();
        if (canMakeNewHome(p)) {
            Home home = new Home(e.getLocation(), p.getUniqueId(), e.getName());
            homeManager.addHome(home);
            sendMessage(p, "Home set");
        } else sendMessage(p, "Not enough homes available. Rankup for more.");
    }

    private boolean canMakeNewHome(Player p) {
        if (p.hasPermission("eonhomes.amount.unlimited")) {
            return true;
        } else {
            CompletableFuture<Long> currentAmountFuture = homeManager.getAmountOfHomes(p.getUniqueId());
            long currentAmount = currentAmountFuture.join();
            int maxAmount = homeManager.getMaxAmountOfHomes(p);
            return currentAmount < maxAmount;
        }
    }

    private void sendMessage(Player player, String message) {
        Bukkit.getScheduler().runTask(plugin,
                () -> player.sendMessage(Utils.chat(EonHomes.prefix + "&7" + message)));
    }
}
