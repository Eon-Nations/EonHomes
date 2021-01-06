package me.squid.eonhomes.commands;

import me.squid.eonhomes.EonHomes;
import net.luckperms.api.LuckPerms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetGroupCommand implements CommandExecutor {

    EonHomes plugin;

    public GetGroupCommand(EonHomes plugin) {
        this.plugin = plugin;
        plugin.getCommand("getgroup").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        LuckPerms api = EonHomes.api;
        String group = api.getUserManager().getUser(p.getUniqueId()).getPrimaryGroup();

        p.sendMessage(group);
        return true;
    }
}
