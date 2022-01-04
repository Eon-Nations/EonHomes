package me.squid.eonhomes.commands;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.event.RemoveHomeEvent;
import me.squid.eonhomes.managers.HomeManager;
import me.squid.eonhomes.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.concurrent.CompletableFuture;

public class DelHomeCommand implements CommandExecutor, Listener {

    EonHomes plugin;
    HomeManager homeManager;

    public DelHomeCommand(EonHomes plugin, HomeManager homeManager) {
        this.plugin = plugin;
        plugin.getCommand("delhome").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 1) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin,
                        () -> Bukkit.getPluginManager().callEvent(new RemoveHomeEvent(p, args[0])));
            } else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Usage: /delhome <home>"));
        }
        return true;
    }

    @EventHandler
    public void onRemoveHomeEventTrigger(RemoveHomeEvent e) {
        Player p = e.getPlayer();
        CompletableFuture<Boolean> homeFuture = homeManager.homeExists(p.getUniqueId(), e.getName());
        homeFuture.thenAcceptAsync(exists -> {
            if (exists) {
                homeManager.removeHome(p.getUniqueId(), e.getName());
                sendMessage(p, "Home removed");
            } else sendMessage(p, "Home not found");
        });
    }

    private void sendMessage(Player player, String message) {
        Bukkit.getScheduler().runTask(plugin,
                () -> player.sendMessage(Utils.chat(EonHomes.prefix + "&7" + message)));
    }
}
