package me.exerosis.packet.player.injection.packets;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketPlayOutCamera;
import org.bukkit.entity.Entity;

public class Camera implements PreconPacket {
    private Packet _packet;

    public Camera(Entity entity) {
        _packet = new PacketPlayOutCamera();
        Reflect.Field(_packet, int.class, "a").setValue(entity.getEntityId());
    }

    @Override
    public void send(PacketPlayer player) {
        player.sendPacket(_packet);
    }
}