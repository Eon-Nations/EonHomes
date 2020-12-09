package me.squid.eonhomes.commands;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.Home;
import me.squid.eonhomes.utils.HomeManager;
import me.squid.eonhomes.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class HomesCommand implements CommandExecutor {

    EonHomes plugin;
    List<Home> homes;

    public HomesCommand(EonHomes plugin) {
        this.plugin = plugin;
        plugin.getCommand("homes").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p;
            if (args.length == 1 && sender.hasPermission("eonhomes.commands.homes.others")) {
                p = Bukkit.getPlayer(args[0]);
                if (p == null) return true;
            }
            else p = (Player) sender;

            if (!HomeManager.isInMap(p)) {
                p.sendMessage(Utils.chat(EonHomes.prefix + "&7No homes found."));
                return true;
            }
            homes = HomeManager.getHomes(p);
            String[] homeArray = new String[homes.size()];

            for (int i = 0; i < homes.size(); i++) {
                homeArray[i] = homes.get(i).getName();
            }
            String hString = getFormattedString(homeArray);

            p.sendMessage(Utils.chat(EonHomes.prefix + "&7Homes: &b" + hString));
        }

        return true;
    }

    private String getFormattedString(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(", ");
        }
        String allArgs = sb.toString().trim();
        return Utils.chat(allArgs);
    }
}
