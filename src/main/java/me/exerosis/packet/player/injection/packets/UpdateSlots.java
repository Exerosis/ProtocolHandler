package me.exerosis.packet.player.injection.packets;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;

import java.util.List;

public class UpdateSlots implements PreconPacket {
    private Object packet;

    public UpdateSlots(List<Object> itemStacks) {
        packet = Reflect.Class("{nms}PacketPlayOutWindowItems").newInstance(0, itemStacks);
    }

    public void send(PacketPlayer player) {
        player.sendPacket(packet);
    }
}
