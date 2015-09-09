package me.exerosis.packet.player.injection.packets;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;

public class SpawnEntity implements PreconPacket {
    private Object packet;

    public SpawnEntity(Object entityLiving) {
        packet = Reflect.Class("{nms}.PacketPlayOutSpawnEntityLiving").newInstance(entityLiving);
    }

    public void send(PacketPlayer player) {
        player.sendPacket(packet);
    }
}