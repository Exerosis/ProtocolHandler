package me.exerosis.packet.utils;

import me.exerosis.reflection.Reflect;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by The Exerosis on 8/22/2015.
 */
public final class PlayerUtil {
    private PlayerUtil() {
    }

    @SuppressWarnings("unchecked")
    public static List<Player> getOnlinePlayers() {
        List<Player> players = new ArrayList<>();
        CraftServer server = (CraftServer) Bukkit.getServer();
        List<CraftPlayer> playerView = Reflect.Field(server, List.class, "playerView").getValue();
        playerView.forEach(player -> players.add((Player) player));
        return players;
    }
}