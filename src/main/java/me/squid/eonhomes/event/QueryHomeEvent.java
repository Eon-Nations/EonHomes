package me.squid.eonhomes.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class QueryHomeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    UUID target, sender;
    String name;

    public QueryHomeEvent(UUID target, UUID sender, String name) {
        super(true);
        this.target = target;
        this.sender = sender;
        this.name = name;
    }

    public UUID getTarget() {
        return target;
    }

    public UUID getSender() {
        return sender;
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
