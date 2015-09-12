package me.exerosis.packet.wrappers.in;

import me.exerosis.packet.utils.packet.PacketUtil;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class PacketWrapperInBlockDig extends PacketWrapper {
    private byte status;
    private Object blockPosition;
    private byte face;

    public PacketWrapperInBlockDig(Object packet) {
        super(packet);
    }

    public void setLocation(Location location) {
        setLocation(location.toVector());
    }

    public void setLocation(Vector location) {
        Reflect.Field(getPacket(), Object.class, 0).setValue(PacketUtil.toBlockLocation(location));
    }

    public Vector getLocation() {
        return PacketUtil.fromBlockPosition(Reflect.Field(getPacket(), Object.class, 0).getValue());
    }

    public byte getFace() {
        return Reflect.Field(getPacket(), byte.class, 1).getValue();
    }

    public void setFace(byte face) {
        Reflect.Field(getPacket(), byte.class, 1).setValue(face);
    }

    public byte getStatus() {
        return Reflect.Field(getPacket(), byte.class, 0).getValue();
    }

    public void setStatus(byte status) {
        Reflect.Field(getPacket(), byte.class, 0).setValue(status);
    }
}