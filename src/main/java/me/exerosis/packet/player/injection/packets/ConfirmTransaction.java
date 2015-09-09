package me.exerosis.packet.player.injection.packets;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import net.minecraft.server.v1_8_R1.PacketPlayOutTransaction;

public class ConfirmTransaction implements PreconPacket {
    private short actionNumber;
    private int windowId;
    private PacketPlayOutTransaction packet;

    public ConfirmTransaction(int windowId, short actionNumber) {
        super();
        this.actionNumber = actionNumber;
        this.windowId = windowId;
        packet = new PacketPlayOutTransaction(windowId, actionNumber, true);
    }

    public void send(PacketPlayer player) {
        player.sendPacket(packet);
    }

    public short getActionNumber() {
        return actionNumber;
    }

    public int getWindowId() {
        return windowId;
    }
}
