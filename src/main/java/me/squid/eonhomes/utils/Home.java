package me.squid.eonhomes.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

public class Home implements Serializable {

    private final Map<String, Object> location;
    private final UUID uuid;
    private final String name;

    public Home(Map<String, Object> location, UUID uuid, String name) {
        this.location = location;
        this.uuid = uuid;
        this.name = name;
    }

    public Location getLocation() {
        return Location.deserialize(location);
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

}
