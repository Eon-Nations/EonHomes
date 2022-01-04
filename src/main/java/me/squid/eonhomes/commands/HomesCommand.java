package me.squid.eonhomes.commands;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.managers.HomeManager;
import me.squid.eonhomes.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HomesCommand implements CommandExecutor {

    EonHomes plugin;
    HomeManager homeManager;

    public HomesCommand(EonHomes plugin, HomeManager homeManager) {
        this.plugin = plugin;
        this.homeManager = homeManager;
        plugin.getCommand("homes").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {
            if (args.length == 0) {
                CompletableFuture<List<String>> homesFuture = homeManager.getHomes(p.getUniqueId());
                homesFuture.thenAcceptAsync(homes -> {
                    if (homes.size() == 0) {
                        p.sendMessage(Utils.chat(EonHomes.prefix +
                                "&7Looks like you don't have any homes. Do /sethome <name> to make a home."));
                    } else {
                        String hString = getFormattedString(homes);
                        p.sendMessage(Utils.chat(EonHomes.prefix + "&7Homes: &b" + hString));
                    }
                });
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
