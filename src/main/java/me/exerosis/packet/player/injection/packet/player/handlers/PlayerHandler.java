package me.exerosis.packet.player.injection.packet.player.handlers;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.player.injection.packets.PreconPacket;
import net.minecraft.server.v1_8_R1.Packet;
import org.bukkit.entity.Player;

//TODO Reformat java docs on this class.
public class PlayerHandler extends PlayerJoinQuitHandler {
    private static PlayerHandler instance;

    private PlayerHandler() {
        super();
    }

    /**
     * Sends a pre-constructed packet each player.
     *
     * @param packet
     */
    public static void sendGroupPreconPacket(PreconPacket packet, PacketPlayer... players) {
        for (PacketPlayer player : players) {
            packet.send(player);
        }
    }

    /**
     * Sends a pre-constructed packet each player.
     *
     * @param packet
     */
    public static void sendGroupPreconPacket(PreconPacket packet, Player... players) {
        for (Player player : players) {
            packet.send(PlayerHandler.getPlayer(player));
        }
    }

    /**
     * Sends a pre-constructed packet to every player on the server.
     *
     * @param packet
     */
    public static void sendGlobalPacket(PreconPacket packet) {
        for (PacketPlayer player : getOnlinePlayers()) {
            packet.send(player);
        }
    }

    /**
     * Sends a packet to every player on the server.
     *
     * @param packet
     */
    public static void sendGlobalPacket(Packet packet) {
        for (PacketPlayer player : getOnlinePlayers()) {
            player.sendPacket(packet);
        }
    }

    /**
     * Sends a packet to the server from every player on the server.
     *
     * @param packet
     */
    public static void receiveGlobalPacket(Packet packet) {
        for (PacketPlayer player : getOnlinePlayers()) {
            player.receivePacket(packet);
        }
    }

    /**
     * Sends a packet to every player on the server.
     *
     * @param packet
     */
    public static void receiveGlobalPacket(PreconPacket packet) {
        for (PacketPlayer player : getOnlinePlayers()) {
            packet.send(player);
        }
    }

    //Singleton
    public static PlayerHandler getInstance() {
        if (instance == null)
            instance = new PlayerHandler();
        return instance;
    }
}
