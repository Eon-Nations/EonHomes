package me.squid.eonhomes;

import me.squid.eonhomes.commands.*;
import me.squid.eonhomes.sql.MySQL;
import me.squid.eonhomes.sql.SQLManager;
import me.squid.eonhomes.tasks.SQLConnectionTask;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class EonHomes extends JavaPlugin {

    public static final String prefix = "&7[&b&lEonHomes&7] ";
    public static LuckPerms api;
    public static MySQL sql;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupCommands();
        setupLuckPerms();
        setupMySQL();
    }

    @Override
    public void onDisable() {
        sql.disconnect();
    }

    private void setupCommands() {
        new SetHomeCommand(this);
        new DelHomeCommand(this);
        new HomeCommand(this);
        new HomesCommand(this);
    }

    private void setupLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            api = provider.getProvider();
        } else System.out.println("LuckPerms is returning null");
    }

    private void setupMySQL() {
        sql = new MySQL(this);
        SQLManager sqlManager = new SQLManager();
        try {
            sql.connectToDatabase();
        } catch (SQLException e) {
            getLogger().warning("SQL Database connection has failed.");
        }
        if (sql.isConnected()) {
            sqlManager.createMainTable();
        }
    }
}
