package me.squid.eonhomes.utils;

import java.util.HashMap;

public enum Group {
    DEFAULT,
    TRAVELER,
    EXPLORER,
    RANGER,
    SPACEMAN,
    ASTRONAUT,
    MYTHIC,
    GAMER,
    BUILDER
    ;
    private static final HashMap<Group, Integer> homeValues = new HashMap<>();

    public static HashMap<Group, Integer> getHomeValues() {
        homeValues.put(DEFAULT, 1);
        homeValues.put(TRAVELER, 2);
        homeValues.put(EXPLORER, 3);
        homeValues.put(RANGER, 5);
        homeValues.put(SPACEMAN, 7);
        homeValues.put(ASTRONAUT, 9);
        homeValues.put(MYTHIC, 15);
        homeValues.put(GAMER, 40);
        homeValues.put(BUILDER, 100);
        return homeValues;
    }
}
