package me.squid.eonhomes.sql;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.utils.Home;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLManager {

    public void createMainTable() {
        Connection connection = EonHomes.sql.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS homes (\n" +
                    "  UUID VARCHAR(100), \n" +
                    "  HOMENAME VARCHAR(100), \n" +
                    "  NAME VARCHAR(100), \n" +
                    "  X INT(100), \n" +
                    "  Y INT(100), \n" +
                    "  Z INT(100), \n" +
                    "  WORLD VARCHAR(100), \n" +
                    "  PITCH FLOAT(10), \n" +
                    "  YAW FLOAT(10), \n" +
                    "  PRIMARY KEY (UUID, HOMENAME)\n" +
                    ")");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addHome(Home home) {
        Connection connection = EonHomes.sql.getConnection();
        try {
            if (!homeExists(home.getUUID(), home.getName())) {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO homes VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, home.getUUID().toString());
                ps.setString(2, home.getName());
                ps.setString(3, Bukkit.getOfflinePlayer(home.getUUID()).getName());
                ps.setInt(4, home.getLocation().getBlockX());
                ps.setInt(5, home.getLocation().getBlockY());
                ps.setInt(6, home.getLocation().getBlockZ());
                ps.setString(7, home.getLocation().getWorld().getName());
                ps.setFloat(8, home.getLocation().getPitch());
                ps.setFloat(9, home.getLocation().getYaw());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeHome(UUID uuid, String name) {
        Connection connection = EonHomes.sql.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM homes WHERE UUID=? AND HOMENAME=?");
            ps.setString(1, uuid.toString());
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Home getHome(UUID uuid, String name) {
        Connection connection = EonHomes.sql.getConnection();

        int x = 0, y = 0, z = 0;
        float yaw = 0, pitch = 0;
        String world = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM homes WHERE UUID=? AND HOMENAME=?");
            ps.setString(1, uuid.toString());
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                rs.getString("NAME");
                x = rs.getInt("X");
                y = rs.getInt("Y");
                z = rs.getInt("Z");
                world = rs.getString("WORLD");
                pitch = rs.getFloat("PITCH");
                yaw = rs.getFloat("YAW");
            }
            Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
            return new Home(loc.serialize(), uuid, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getHomes(UUID uuid) {
        Connection connection = EonHomes.sql.getConnection();
        List<String> homes = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM homes WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("HOMENAME");
                homes.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return homes;
    }

    public List<String> getHomes(String name) {
        Connection connection = EonHomes.sql.getConnection();
        List<String> homes = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM homes WHERE NAME=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                homes.add(rs.getString("HOMENAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return homes;
    }

    public boolean homeExists(UUID uuid, String name) {
        Connection connection = EonHomes.sql.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM homes WHERE UUID=? AND HOMENAME=?");
            ps.setString(1, uuid.toString());
            ps.setString(2, name);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getAmountOfHomes(UUID uuid) {
        int count = 0;
        Connection connection = EonHomes.sql.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM homes WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
