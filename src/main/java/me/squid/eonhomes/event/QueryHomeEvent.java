package me.squid.eonhomes.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class QueryHomeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    UUID uuid;
    String name;

    public QueryHomeEvent(UUID uuid, String name) {
        super(true);
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
