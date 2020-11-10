package me.squid.eonhomes.commands;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.event.NewHomeEvent;
import me.squid.eonhomes.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {

    EonHomes plugin;

    public SetHomeCommand(EonHomes plugin) {
        this.plugin = plugin;
        plugin.getCommand("sethome").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> Bukkit.getPluginManager().callEvent(new NewHomeEvent(p, p.getLocation(), args[0])));
            } else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Usage: /sethome <home>"));
        }

        System.out.println("Sethome command has been executed");
        return true;
    }
}
