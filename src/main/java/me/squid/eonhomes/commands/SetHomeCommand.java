package me.squid.eonhomes.commands;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.sql.SQLManager;
import me.squid.eonhomes.utils.Home;
import me.squid.eonhomes.utils.Group;
import me.squid.eonhomes.utils.Utils;
import net.luckperms.api.LuckPerms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SetHomeCommand implements CommandExecutor {

    EonHomes plugin;

    public SetHomeCommand(EonHomes plugin) {
        this.plugin = plugin;
        plugin.getCommand("sethome").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SQLManager sqlManager = new SQLManager();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                if (canMakeNewHome(p, sqlManager)) {
                    Home home = new Home(p.getLocation().serialize(), p.getUniqueId(), args[0]);
                    sqlManager.addHome(home);
                    p.sendMessage(Utils.chat(EonHomes.prefix + "&7Home set"));
                } else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Not enough homes available. Rankup for more."));
            } else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Usage: /sethome <home>"));
        }

        System.out.println("Sethome command has been executed");
        return true;
    }

    private boolean canMakeNewHome(Player p, SQLManager sqlManager) {
        if (p.hasPermission("eonhomes.amount.unlimited")) {
            return true;
        } else {
            int currentAmount = sqlManager.getAmountOfHomes(p.getUniqueId());
            int maxAmount = getMaxAmountOfHomes(p);
            return currentAmount < maxAmount;
        }
    }

    private int getMaxAmountOfHomes(Player p) {
        LuckPerms api = EonHomes.api;
        HashMap<Group, Integer> homeValues = Group.getHomeValues();

        String pgroup = api.getUserManager().getUser(p.getUniqueId()).getPrimaryGroup();

        for (Group g : Group.values()) {
            Group group = Group.valueOf(pgroup.toUpperCase());
            if (group.equals(g)) return homeValues.get(g);
        }
        return 1;
    }
}
