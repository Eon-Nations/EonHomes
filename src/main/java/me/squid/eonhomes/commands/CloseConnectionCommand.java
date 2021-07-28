package me.squid.eonhomes.commands;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.sql.MySQL;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CloseConnectionCommand implements CommandExecutor {

    EonHomes plugin;

    public CloseConnectionCommand(EonHomes plugin) {
        this.plugin = plugin;
        plugin.getCommand("breakhomes").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        MySQL sql = EonHomes.sql;

        if (sql.isConnected()) {
            sql.disconnect();
        } else {
            Player p = (Player) commandSender;
            p.sendMessage(Component.text("Connection already closed").color(TextColor.color(255, 0, 0)));
        }

        return true;
    }
}
