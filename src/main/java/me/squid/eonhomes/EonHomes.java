package me.squid.eonhomes;

import me.squid.eonhomes.commands.DelHomeCommand;
import me.squid.eonhomes.commands.HomeCommand;
import me.squid.eonhomes.commands.HomesCommand;
import me.squid.eonhomes.commands.SetHomeCommand;
import me.squid.eonhomes.files.Homes;
import me.squid.eonhomes.listeners.HomeChangeListener;
import me.squid.eonhomes.utils.HomeManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class EonHomes extends JavaPlugin {

    public static final String prefix = "&7[&b&lEonHomes&7] ";

    @Override
    public void onEnable() {
        loadHomesFile();
        setupHomeFile();
        setupCommands();
        setupListeners();
    }

    @Override
    public void onDisable() {
        try {
            HomeManager.saveMapToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupHomeFile() {
        Homes homes = new Homes();
        homes.setupFile();
        homes.saveConfig();
        homes.loadDefaults();
    }

    private void setupCommands() {
        new SetHomeCommand(this);
        new DelHomeCommand(this);
        new HomeCommand(this);
        new HomesCommand(this);
    }

    private void setupListeners() {
        new HomeChangeListener(this);
    }

    private void loadHomesFile() {
        try {
            HomeManager.loadMapFromFile();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
