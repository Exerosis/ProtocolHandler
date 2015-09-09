package me.exerosis.packet.player.injection.packets;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;
import me.exerosis.reflection.ReflectClass;

public class Animation implements PreconPacket {
    private ReflectClass<Object> packet;

    public Animation(int entityId, int animationId) {
        packet = Reflect.Class("{nms}PacketPlayOutAnimation");
        packet.newInstance();
        Reflect.Field(packet, int.class, "a").setValue(entityId);
        Reflect.Field(packet, int.class, "b").setValue(animationId);
    }

    public void send(PacketPlayer player) {
        player.sendPacket(packet.getInstance());
    }
}
