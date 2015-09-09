package me.exerosis.packet.player.injection.events.out.entity.move;

import me.exerosis.packet.player.injection.events.out.entity.PacketEventOutEntity;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.utils.location.LocationUtils;
import me.exerosis.reflection.Reflect;

public class PacketEventOutRelEntityMove extends PacketEventOutEntity {
    /*
        double x
        double y
        double z
        boolean onGround
     */
    public PacketEventOutRelEntityMove(Object packet, PacketPlayer player) {
        super(packet, player);
    }

    public double getX() {
        return LocationUtils.fromPointForm(Reflect.Field(getPacket(), byte.class, 0).getValue());
    }

    public void setX(double x) {
        Reflect.Field(getPacket(), byte.class, 0).setValue(LocationUtils.toPointForm(x));
    }

    public double getY() {
        return LocationUtils.fromPointForm(Reflect.Field(getPacket(), byte.class, 1).getValue());
    }

    public void setY(double y) {
        Reflect.Field(getPacket(), byte.class, 1).setValue(LocationUtils.toPointForm(y));
    }

    public double getZ() {
        return LocationUtils.fromPointForm(Reflect.Field(getPacket(), byte.class, 2).getValue());
    }

    public void setZ(double z) {
        Reflect.Field(getPacket(), byte.class, 2).setValue(LocationUtils.toPointForm(z));
    }

    public boolean getOnGround() {
        return Reflect.Field(getPacket(), boolean.class, 0).getValue();
    }

    public void setOnGround(boolean onGround) {
        Reflect.Field(getPacket(), boolean.class, 0).setValue(onGround);
    }
}