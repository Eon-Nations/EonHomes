package me.squid.eonhomes.utils;

import java.util.HashMap;

public enum Group {
    DEFAULT,
    TRAVELER,
    EXPLORER,
    RANGER,
    SPACEMAN,
    ASTRONAUT,
    ALIEN,
    GAMER,
    BUILDER
    ;
    private static final HashMap<Group, Integer> homeValues = new HashMap<>();

    public static HashMap<Group, Integer> getHomeValues() {
        homeValues.put(DEFAULT, 1);
        homeValues.put(TRAVELER, 3);
        homeValues.put(EXPLORER, 5);
        homeValues.put(RANGER, 7);
        homeValues.put(SPACEMAN, 9);
        homeValues.put(ASTRONAUT, 15);
        homeValues.put(ALIEN, 25);
        homeValues.put(GAMER, 40);
        homeValues.put(BUILDER, 100);
        return homeValues;
    }
}
