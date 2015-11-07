package me.exerosis.packet.injection.handlers;

import me.exerosis.packet.injection.PacketPlayer;
import net.minecraft.server.v1_8_R1.Packet;

//TODO Reformat java docs on this class.
public class PlayerHandler extends PlayerJoinQuitHandler {
    private static PlayerHandler instance;

    private PlayerHandler() {
        super();
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

    //Singleton
    public static PlayerHandler getInstance() {
        if (instance == null)
            instance = new PlayerHandler();
        return instance;
    }
}
