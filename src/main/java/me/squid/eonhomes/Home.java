package me.squid.eonhomes;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Home {

    private Location location;
    private Player owner;
    private String name;

    public Home(Location location, Player owner, String name) {
        this.location = location;
        this.owner = owner;
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public Player getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

}
