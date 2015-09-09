package me.exerosis.packet.player.injection.packets;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;

public class UpdateTime implements PreconPacket {

    private Object packet;

    public UpdateTime(long worldAge, long time) {
        packet = Reflect.Class("{nms}PacketPlayOutUpdateTime").newInstance(worldAge, time, false);
    }

    public void send(PacketPlayer player) {
        player.sendPacket(packet);
    }
}
