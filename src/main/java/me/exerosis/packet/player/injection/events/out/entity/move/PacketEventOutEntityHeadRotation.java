package me.exerosis.packet.player.injection.events.out.entity.move;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.utils.location.LocationUtils;
import me.exerosis.reflection.Reflect;

public class PacketEventOutEntityHeadRotation extends PacketEventOutRelEntityMove {
    /*
    double yaw
    double pitch
     */
    public PacketEventOutEntityHeadRotation(Object packet, PacketPlayer player) {
        super(packet, player);
    }

    public double getYaw() {
        return LocationUtils.fromNMSAngleForm(Reflect.Field(getPacket(), byte.class, 3).getValue());
    }

    public void setYaw(double yaw) {
        Reflect.Field(getPacket(), byte.class, 3).setValue(LocationUtils.toNMSAngleForm(yaw));
    }

    public double getPitch() {
        return LocationUtils.fromNMSAngleForm(Reflect.Field(getPacket(), byte.class, 4).getValue());
    }

    public void setPitch(double pitch) {
        Reflect.Field(getPacket(), byte.class, 4).setValue(LocationUtils.toNMSAngleForm(pitch));
    }
}