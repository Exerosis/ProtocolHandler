package me.exerosis.packet.player.injection.packets;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;
import me.exerosis.reflection.ReflectClass;
import org.bukkit.Location;

public class UseBed implements PreconPacket {
    private ReflectClass<Object> packet;

    public UseBed(PacketPlayer player, Location location) {
        packet = Reflect.Class("{nms}.PacketPlayOutBed");
        if (packet == null)
            return;
        packet.newInstance();
        packet.getField(int.class, 0).setValue(player.getCraftPlayer().getId());
        packet.getField("b").setValue(Reflect.Class("{nms}.BlockPosition").newInstance(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    public void send(PacketPlayer player) {
        player.sendPacket(packet.getInstance());
    }
}
