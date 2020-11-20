package me.squid.eonhomes.commands;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.event.RemoveHomeEvent;
import me.squid.eonhomes.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHomeCommand implements CommandExecutor {

    EonHomes plugin;

    public DelHomeCommand(EonHomes plugin) {
        this.plugin = plugin;
        plugin.getCommand("delhome").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1)
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> Bukkit.getPluginManager().callEvent(new RemoveHomeEvent(p, args[0])));
            else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Usage: /delhome <home>"));
        }
        return true;
    }
}
