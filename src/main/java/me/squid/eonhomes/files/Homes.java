package me.squid.eonhomes.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Homes {

    private static File file;
    private static FileConfiguration homesConfig;

    public void setupFile() {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("EonHomes")).getDataFolder(), "homes.yml");

        if (!file.exists()) {
            try {
                if (file.createNewFile()) System.out.println("Homes.yml file successful");
            } catch (IOException e) {
                // Blank
            }
        }
        homesConfig = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return homesConfig;
    }

    public void saveConfig() {
        try {
            homesConfig.save(file);
        } catch (IOException e) {
            System.out.println("System failed to save config");
        }
    }

    public void reload() {
        homesConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void loadDefaults() {
        homesConfig.createSection("Homes");
    }

}
