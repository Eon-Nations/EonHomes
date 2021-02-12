package me.squid.eonhomes.tasks;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.sql.MySQL;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class SQLConnectionTask extends BukkitRunnable {

    EonHomes plugin;
    MySQL sql;

    public SQLConnectionTask(EonHomes plugin, MySQL sql) {
        this.plugin = plugin;
        this.sql = sql;
    }


    @Override
    public void run() {
        if (!sql.isConnected()) {
            try {
                sql.connectToDatabase();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
