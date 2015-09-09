package me.exerosis.packet.player.injection.packet.player.handlers;

import me.exerosis.packet.PacketAPI;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.Collection;
import java.util.HashMap;

//TODO Reformat java docs on this class.
@SuppressWarnings("deprecation")
public class PlayerJoinQuitHandler implements Listener {
    protected static HashMap<Player, PacketPlayer> packetPlayers = new HashMap<>();

    protected PlayerJoinQuitHandler() {
        Bukkit.getPluginManager().registerEvents(this, PacketAPI.getPlugin());
    }

    /**
     * Gets an online PacketPlayer.
     *
     * @param player
     * @return packetPlayer
     */
    public static PacketPlayer getPlayer(Player player) {
        return packetPlayers.get(player);
    }

    /**
     * Gets every online PacketPlayer.
     *
     * @return Collection<PacketPlayer>
     */
    public static Collection<PacketPlayer> getOnlinePlayers() {
        return packetPlayers.values();
    }

    @SuppressWarnings("static-method")
    @EventHandler
    public void onEnable(PluginEnableEvent event) {
        if (!event.getPlugin().equals(PacketAPI.getPlugin()))
            return;
        for (Player player : PlayerUtil.getOnlinePlayers()) {
            packetPlayers.put(player, new PacketPlayer(player));
        }
    }

    @SuppressWarnings("static-method")
    @EventHandler
    public void onDisable(PluginDisableEvent event) {
        if (!event.getPlugin().equals(PacketAPI.getPlugin()))
            return;
        for (Player onlinePlayer : PlayerUtil.getOnlinePlayers()) {
            PacketPlayer player = packetPlayers.remove(onlinePlayer);
            player.unInject();
        }
    }

    @SuppressWarnings("static-method")
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        packetPlayers.put(event.getPlayer(), new PacketPlayer(event.getPlayer()));
    }

    @SuppressWarnings("static-method")
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PacketPlayer player = packetPlayers.remove(event.getPlayer());
        player.unInject();
    }
}
