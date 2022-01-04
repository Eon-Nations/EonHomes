package me.squid.eonhomes.managers;

import me.squid.eonhomes.EonHomes;
import me.squid.eonhomes.utils.Group;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.MetaNode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class HomeManager {

    EonHomes plugin;
    LuckPerms luckPerms;

    public HomeManager(EonHomes plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    public void addHome(Home home) {
        luckPerms.getUserManager().modifyUser(home.getUUID(), user -> {
            MetaNode node = MetaNode.builder("home:" + home.getName(), home.toString()).build();
            // To ensure no duplicates arise
            user.data().clear(NodeType.META.predicate(mn -> mn.getMetaKey().equals("home:" + home.getName())));
            user.data().add(node);
        });
    }

    public void removeHome(UUID uuid, String name) {
        luckPerms.getUserManager().modifyUser(uuid, user -> {
            Node toRemove = Node.builder("home:" + name).build();
            user.data().remove(toRemove);
        });
    }

    public Home getHome(UUID uuid, String name) {
        CompletableFuture<Home> homeFuture = luckPerms.getUserManager().loadUser(uuid).thenApplyAsync(user -> {
            List<MetaNode> metaNodes = new ArrayList<>();
            MetaNode node = user.getNodes(NodeType.META).stream()
                    .filter(mn -> mn.getMetaKey().equals("home:" + name))
                    .findFirst().orElseThrow();
            plugin.getLogger().info("Node MetaValue: " + node.getMetaValue());
            return fromHomeString(uuid, node.getMetaValue());
        });
        return homeFuture.join();
    }

    public List<String> getHomes(UUID uuid) {
        List<String> homeList = new ArrayList<>();
        luckPerms.getUserManager().loadUser(uuid).thenAcceptAsync(user -> {
            Collection<MetaNode> nodeList = user.getNodes(NodeType.META);
            nodeList.forEach(node -> plugin.getLogger().info("Node: " + node.getMetaKey()));
            nodeList.stream().filter(node -> node.getMetaKey().split(":")[0].equals("home"))
                    .forEach(node -> homeList.add(node.getMetaKey().split(":")[1]));
        });
        return homeList;
    }

    public CompletableFuture<Boolean> homeExists(UUID uuid, String name) {
        return luckPerms.getUserManager().loadUser(uuid)
                .thenApplyAsync(user -> user.getNodes(NodeType.META).stream()
                        .anyMatch(node -> node.getMetaKey().equals("home:" + name)));
    }

    public CompletableFuture<Long> getAmountOfHomes(UUID uuid) {
        return luckPerms.getUserManager().loadUser(uuid).thenApplyAsync(user ->
                user.getNodes(NodeType.META).stream()
                .filter(node -> node.getMetaKey().contains("home"))
                .count());
    }

    public int getMaxAmountOfHomes(Player p) {
        HashMap<Group, Integer> homeValues = Group.getHomeValues();
        User user = luckPerms.getUserManager().loadUser(p.getUniqueId()).join();

        String pgroup = user.getPrimaryGroup();
        for (Group g : Group.values()) {
            Group group = Group.valueOf(pgroup.toUpperCase());
            if (group.equals(g)) return homeValues.get(g);
        }
        return 1;
    }

    private Home fromHomeString(UUID uuid, String homeString) {
        String[] splitString = homeString.split(";");
        String name = splitString[0];
        int x = Integer.parseInt(splitString[1]);
        int y= Integer.parseInt(splitString[2]);
        int z = Integer.parseInt(splitString[3]);
        World world = Bukkit.getWorld(splitString[4]);
        float pitch = Float.parseFloat(splitString[5]);
        float yaw = Float.parseFloat(splitString[6]);

        Location homeLocation = new Location(world, x, y, z, yaw, pitch);
        return new Home(homeLocation, uuid, name);
    }
}
