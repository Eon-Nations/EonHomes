package me.squid.eonhomes.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RemoveHomeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player p;
    private String name;

    public RemoveHomeEvent(Player p, String name) {
        super(true);
        this.p = p;
        this.name = name;
    }

    public Player getPlayer() {
        return p;
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
