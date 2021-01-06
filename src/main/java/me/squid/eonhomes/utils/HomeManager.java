package me.squid.eonhomes.utils;

import me.squid.eonhomes.Home;
import me.squid.eonhomes.files.Homes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class HomeManager {

    private static HashMap<UUID, ArrayList<Home>> homeMap = new HashMap<>();

    public static void addHome(Home home) {

        ArrayList<Home> homes;
        if (homeMap.containsKey(home.getUUID())) {
            homes = homeMap.get(home.getUUID());
        } else homes = new ArrayList<>();

        homes.add(home);
        homeMap.put(home.getUUID(), homes);
    }

    public static void removeHome(Home home) {

        if (homeMap.containsKey(home.getUUID())) {
            ArrayList<Home> homes = homeMap.get(home.getUUID());
            homes.remove(home);
            homeMap.put(home.getUUID(), homes);
        }
    }

    public static Home getHome(Player p, String name) {
        if (homeMap.containsKey(p.getUniqueId())) {
            ArrayList<Home> homes = homeMap.get(p.getUniqueId());
            for (Home home : homes) {
                if (home.getName().equalsIgnoreCase(name)) {
                    return home;
                }
            }
        }
        return null;
    }

    public static Home getHome(UUID uuid, String name) {
        if (homeMap.containsKey(uuid)) {
            ArrayList<Home> homes = homeMap.get(uuid);
            for (Home home : homes) {
                if (home.getName().equalsIgnoreCase(name)) {
                    return home;
                }
            }
        }
        return null;
    }

    public static ArrayList<Home> getHomes(Player p) {
        return homeMap.get(p.getUniqueId());
    }

    public static boolean isInMap(Player p) {
        return homeMap.containsKey(p.getUniqueId());
    }

    public static void addPlayerToMap(Player p) {
        homeMap.put(p.getUniqueId(), new ArrayList<>());
    }

    public static World getWorld(String w) {
        boolean loop = true;
        int index = 0;
        do {
            if (Bukkit.getWorlds().get(index).getName().equals(w)) {
                return Bukkit.getWorlds().get(index);
            }
            index++;
            if (index == Bukkit.getWorlds().size()) loop = false;
        } while (loop);
        return null;
    }

    public static void saveMapToFile() throws IOException {
        File file = new File(Bukkit.getPluginManager().getPlugin("EonHomes").getDataFolder(), "homemap.ser");
        ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

        output.writeObject(homeMap);
        output.flush();
        output.close();
    }

    public static void loadMapFromFile() throws IOException, ClassNotFoundException {
        File file = new File(Bukkit.getPluginManager().getPlugin("EonHomes").getDataFolder(), "homemap.ser");
        ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));

        Object readObject = input.readObject();
        input.close();

        if (!(readObject instanceof HashMap)) throw new IOException("Data is not in a hashmap");
        //noinspection unchecked
        homeMap = (HashMap<UUID, ArrayList<Home>>) readObject;
    }
}
