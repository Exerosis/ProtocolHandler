package me.exerosis.packet.wrappers.out;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.reflection.Reflect;
import me.exerosis.packet.utils.location.LocationUtils;
import me.exerosis.packet.wrappers.PacketWrapper;

public class PacketEventOutRelEntityMove extends PacketWrapper {
    private double x;
    private double y;
    private double z;

    public PacketEventOutRelEntityMove(Object packet, PacketPlayer player) {
        super(packet, player);
        x = LocationUtils.fromPointForm(Reflect.Field(0, packet, byte.class).getValue());
        y = LocationUtils.fromPointForm(Reflect.Field(1, packet, byte.class).getValue());
        z = LocationUtils.fromPointForm(Reflect.Field(2, packet, byte.class).getValue());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        Reflect.Field(0, getPacket(), byte.class).setValue(LocationUtils.toPointForm(x));
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        Reflect.Field(0, getPacket(), byte.class).setValue(LocationUtils.toPointForm(y));
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
        Reflect.Field(0, getPacket(), byte.class).setValue(LocationUtils.toPointForm(z));
    }
}
