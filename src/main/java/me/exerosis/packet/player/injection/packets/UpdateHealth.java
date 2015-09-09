package me.exerosis.packet.player.injection.packets;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;

public class UpdateHealth implements PreconPacket {

    private Object packet;

    public UpdateHealth(int health, int food, float saturation) {
        packet = Reflect.Class("{nms}PacketPlayOutUpdateHealth").newInstance(health, food, saturation);
    }

    public void send(PacketPlayer player) {
        player.sendPacket(packet);
    }
}
