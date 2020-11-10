package me.squid.eonhomes.event;

import me.squid.eonhomes.Home;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RemoveHomeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player p;
    private Home home;
    private String name;

    public RemoveHomeEvent(Player p, Home home, String name) {
        super(true);
        this.p = p;
        this.home = home;
        this.name = name;
    }

    public Player getPlayer() {
        return p;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
