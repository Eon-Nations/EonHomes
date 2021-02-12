package me.squid.eonhomes.commands;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.sql.SQLManager;
import me.squid.eonhomes.utils.Utils;
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
        SQLManager sqlManager = new SQLManager();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                if (sqlManager.getHome(p.getUniqueId(), args[0]) != null) {
                    sqlManager.removeHome(p.getUniqueId(), args[0]);
                    p.sendMessage(Utils.chat(EonHomes.prefix + "&7Home removed"));
                } else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Home not found."));
            } else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Usage: /delhome <home>"));
        }
        return true;
    }
}
