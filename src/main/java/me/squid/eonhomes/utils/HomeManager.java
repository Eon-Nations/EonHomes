package me.squid.eonhomes.utils;

import me.squid.eonhomes.Home;
import me.squid.eonhomes.files.Homes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HomeManager {

    public static void addHome(Home home) {
        Homes homes = new Homes();
        String key = "Homes." + home.getOwner().getUniqueId().toString();
        homes.getConfig().set(key + "." + home.getName() + ".world", home.getLocation().getWorld().getName());
        homes.getConfig().set(key + "." + home.getName() + ".x", home.getLocation().getBlockX());
        homes.getConfig().set(key + "." + home.getName() + ".y", home.getLocation().getBlockY());
        homes.getConfig().set(key + "." + home.getName() + ".z", home.getLocation().getBlockZ());
        homes.getConfig().set(key + "." + home.getName() + ".yaw", home.getLocation().getYaw());
        homes.getConfig().set(key + "." + home.getName() + ".pitch", home.getLocation().getPitch());
        homes.saveConfig();
        homes.reload();
    }

    public static void removeHome(Home home) {
        Homes homes = new Homes();
        String key = "Homes." + home.getOwner().getUniqueId().toString();

        homes.getConfig().set(key + "." + home.getName() + ".world", null);
        homes.getConfig().set(key + "." + home.getName() + ".x", null);
        homes.getConfig().set(key + "." + home.getName() + ".y", null);
        homes.getConfig().set(key + "." + home.getName() + ".z", null);
        homes.getConfig().set(key + "." + home.getName() + ".yaw", null);
        homes.getConfig().set(key + "." + home.getName() + ".pitch", null);
        homes.getConfig().set(key + "." + home.getName(), null);
        homes.saveConfig();
        homes.reload();
    }

    public static Set<String> getHomes(Player p) {
        Homes homes = new Homes();
        String key = "Homes." + p.getUniqueId().toString();
        return homes.getConfig().getConfigurationSection(key).getKeys(false);
    }

    public static World getWorld(String w) {
        boolean loop = true;
        int index = 0;
        do {
            if (Bukkit.getWorlds().get(index).getName().equals(w))  {
                return Bukkit.getWorlds().get(index);
            }
            index++;
            if (index == Bukkit.getWorlds().size()) loop = false;
        } while (loop);
        return null;
    }
}
