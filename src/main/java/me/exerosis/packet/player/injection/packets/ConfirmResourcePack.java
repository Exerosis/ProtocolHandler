package me.exerosis.packet.player.injection.packets;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;

public class ConfirmResourcePack implements PreconPacket {
    private String hash;
    private Object packet;

    public ConfirmResourcePack(String hash) {
        this.hash = hash;
        packet = Reflect.Class("{nms}PacketPlayInResourcePackStatus").newInstance();

        Reflect.Field(packet, String.class, "a").setValue(hash);
        Reflect.Field(packet, int.class, "b").setValue(3);
    }

    public void send(PacketPlayer player) {
        player.sendPacket(packet);
    }

    public String getHash() {
        return hash;
    }
}
