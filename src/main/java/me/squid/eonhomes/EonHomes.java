package me.squid.eonhomes;

import me.squid.eonhomes.commands.*;
import me.squid.eonhomes.files.Homes;
import me.squid.eonhomes.listeners.HomeChangeListener;
import me.squid.eonhomes.utils.HomeManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class EonHomes extends JavaPlugin {

    public static final String prefix = "&7[&b&lEonHomes&7] ";
    public static LuckPerms api;

    @Override
    public void onEnable() {
        loadHomesFile();
        setupHomeFile();
        setupCommands();
        setupListeners();
        setupLuckPerms();
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
        new GetGroupCommand(this);
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

    private void setupLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            api = provider.getProvider();
        } else System.out.println("LuckPerms is returning null");
    }
}
