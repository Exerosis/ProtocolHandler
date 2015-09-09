package me.exerosis.packet.wrappers.entity.out.move;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.utils.packet.PacketUtil;
import me.exerosis.packet.wrappers.entity.PacketWrapperEntity;
import me.exerosis.reflection.Reflect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class PacketWrapperOutRelEntityMove extends PacketWrapperEntity {
    /*
    double x
    double y
    double z
    boolean onGround
    */
    public PacketWrapperOutRelEntityMove(Object packet) {
        super(packet);
    }

    public PacketWrapperOutRelEntityMove(int id, Vector location, boolean onGround) {
        super(PacketPlay.Out.RelEntityMove(id, location, onGround));
    }

    public PacketWrapperOutRelEntityMove(int id, Location location, boolean onGround) {
        super(PacketPlay.Out.RelEntityMove(id, location, onGround));
    }

    public PacketWrapperOutRelEntityMove(int id, double x, double y, double z, boolean onGround) {
        super(PacketPlay.Out.RelEntityMove(id, x, y, z, onGround));
    }

    public double getX() {
        return PacketUtil.fromPointForm(Reflect.Field(getPacket(), byte.class, 0).getValue());
    }

    public void setX(double x) {
        Reflect.Field(getPacket(), byte.class, 0).setValue(PacketUtil.toPointForm(x));
    }

    public double getY() {
        return PacketUtil.fromPointForm(Reflect.Field(getPacket(), byte.class, 1).getValue());
    }

    public void setY(double y) {
        Reflect.Field(getPacket(), byte.class, 1).setValue(PacketUtil.toPointForm(y));
    }

    public double getZ() {
        return PacketUtil.fromPointForm(Reflect.Field(getPacket(), byte.class, 2).getValue());
    }

    public void setZ(double z) {
        Reflect.Field(getPacket(), byte.class, 2).setValue(PacketUtil.toPointForm(z));
    }

    public boolean getOnGround() {
        return Reflect.Field(getPacket(), boolean.class, 0).getValue();
    }

    public void setOnGround(boolean onGround) {
        Reflect.Field(getPacket(), boolean.class, 0).setValue(onGround);
    }
}