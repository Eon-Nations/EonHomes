package me.squid.eonhomes.commands;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.Home;
import me.squid.eonhomes.files.Homes;
import me.squid.eonhomes.utils.HomeManager;
import me.squid.eonhomes.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HomeCommand implements CommandExecutor {

    EonHomes plugin;
    Home home;

    public HomeCommand(EonHomes plugin) {
        this.plugin = plugin;
        plugin.getCommand("home").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                home = HomeManager.getHome(p, args[0]);
                if (home != null) {
                    if (p.hasPermission("eonhomes.cooldown.bypass")) {
                        p.teleportAsync(home.getLocation());
                        p.sendMessage(Utils.chat(EonHomes.prefix + "&7Successfully teleported to " + home.getName()));
                        return true;
                    } else {
                        p.sendMessage(Utils.chat(EonHomes.prefix + "&7Teleporting in 3 seconds..."));
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            p.teleportAsync(home.getLocation());
                            p.sendMessage(Utils.chat(EonHomes.prefix + "&7Successfully teleported to " + home.getName()));
                        }, 60L);
                    }
                } else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Home is invalid"));
            } else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Usage: /home <name>"));
        }

        return true;
    }
}
