package me.squid.eonhomes.commands;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.Home;
import me.squid.eonhomes.event.RemoveHomeEvent;
import me.squid.eonhomes.files.Homes;
import me.squid.eonhomes.utils.HomeManager;
import me.squid.eonhomes.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHomeCommand implements CommandExecutor {

    EonHomes plugin;
    Homes homes = new Homes();

    public DelHomeCommand(EonHomes plugin) {
        this.plugin = plugin;
        plugin.getCommand("delhome").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 1) {
                if (homes.getConfig().getString("Homes." + p.getUniqueId().toString() + "." + args[0]) != null) {
                    String key = "Homes." + p.getUniqueId().toString() + "." + args[0];
                    World world = HomeManager.getWorld(homes.getConfig().getString(key + ".world"));
                    double x = homes.getConfig().getDouble(key + ".x");
                    double y = homes.getConfig().getDouble(key + ".y");
                    double z = homes.getConfig().getDouble(key + ".z");
                    float yaw = (float) homes.getConfig().getDouble(key + ".yaw");
                    float pitch = (float) homes.getConfig().getDouble(key + ".pitch");
                    Location location = new Location(world, x, y, z, yaw, pitch);
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> Bukkit.getPluginManager().callEvent(new RemoveHomeEvent(p, new Home(location, p, args[0]), args[0])));
                }
                else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Home is invalid"));
            } else p.sendMessage(Utils.chat(EonHomes.prefix + "&7Usage: /delhome <home>"));
        }

        return true;
    }
}
