package me.squid.eonhomes;

import me.squid.eonhomes.commands.*;
import me.squid.eonhomes.managers.HomeManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class EonHomes extends JavaPlugin {

    public static final String prefix = "&7[&b&lEonHomes&7] ";
    LuckPerms luckPerms;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupLuckPerms();
        setupCommands();
    }

    private void setupCommands() {
        HomeManager homeManager = new HomeManager(this, luckPerms);
        new SetHomeCommand(this, homeManager, luckPerms);
        new DelHomeCommand(this, homeManager);
        new HomeCommand(this, homeManager);
        new HomesCommand(this, homeManager);
    }

    private void setupLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        } else {
            getLogger().info("LuckPerms was not successfully registered");
            getServer().shutdown();
        }
    }
}
