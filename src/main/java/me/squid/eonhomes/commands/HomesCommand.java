package me.squid.eonhomes.commands;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.sql.SQLManager;
import me.squid.eonhomes.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HomesCommand implements CommandExecutor {

    EonHomes plugin;

    public HomesCommand(EonHomes plugin) {
        this.plugin = plugin;
        plugin.getCommand("homes").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SQLManager sqlManager = new SQLManager();

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                List<String> homes = sqlManager.getHomes(p.getUniqueId());
                if (homes.size() == 0)
                    p.sendMessage(Utils.chat(EonHomes.prefix + "&7If you have homes, try again in a minute. " +
                            "If you don't, set a home with /sethome and your homes will show up here"));
                else {
                    String hString = getFormattedString(homes);
                    p.sendMessage(Utils.chat(EonHomes.prefix + "&7Homes: &b" + hString));
                }
            } else if (args.length == 1 && p.hasPermission("eonhomes.homes.others")) {
                try {
                    List<String> homes = sqlManager.getHomes(args[0]);
                    String hString = getFormattedString(homes);
                    p.sendMessage(Utils.chat(EonHomes.prefix + "&7Homes: &b" + hString));
                } catch (NullPointerException e) {
                    p.sendMessage(Utils.chat(EonHomes.prefix + "&7Invalid target"));
                }
            }
        }

        return true;
    }

    private String getFormattedString(List<String> args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.size(); i++) {
            if (i == 0) sb.append(args.get(i));
            else sb.append(", ").append(args.get(i));
        }
        String allArgs = sb.toString().trim();
        return Utils.chat(allArgs);
    }
}
