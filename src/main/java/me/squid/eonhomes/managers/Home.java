package me.squid.eonhomes.managers;

import me.squid.eonhomes.utils.Utils;
import org.bukkit.Location;

import java.util.UUID;

public record Home(Location location, UUID uuid, String name) {

    public Location getLocation() {
        return location;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + ";" + location.getBlockX() + ";"
                + location.getBlockY() + ";"
                + location.getBlockZ() + ";"
                + location.getWorld().getName() + ";"
                + Utils.round(location.getPitch(), 2) + ";"
                + Utils.round(location.getYaw(), 2);
    }
}
