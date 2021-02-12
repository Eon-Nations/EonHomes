package me.squid.eonhomes.commands;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.sql.SQLManager;
import me.squid.eonhomes.utils.Home;
import me.squid.eonhomes.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {

    EonHomes plugin;

    public HomeCommand(EonHomes plugin) {
        this.plugin = plugin;
        plugin.getCommand("home").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        SQLManager sqlManager = new SQLManager();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                Home home = sqlManager.getHome(p.getUniqueId(), args[0]);
                if (home != null) {
                    if (p.hasPermission("eonhomes.cooldown.bypass")) {
                        p.teleportAsync(home.getLocation());
                        p.sendMessage(Utils.chat(EonHomes.prefix + "&7Successfully teleported to " + home.getName()));
                    } else {
                        p.sendMessage(Utils.chat(EonHomes.prefix + "&7Teleporting in 3 seconds..."));
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            p.teleportAsync(home.getLocation());
                            p.sendMessage(Utils.chat(EonHomes.prefix + "&7Successfully teleported to " + home.getName()));
                        }, 60L);
                    }
                } else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Home is invalid"));
            } else if (args.length == 2 && p.hasPermission("eonhomes.commands.home.others")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    Home home = sqlManager.getHome(target.getUniqueId(), args[1]);
                    if (home != null) p.teleportAsync(home.getLocation());
                    else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Home is invalid"));
                } else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Player does not exist."));
            } else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Usage: /home <name>"));
        }

        return true;
    }
}
