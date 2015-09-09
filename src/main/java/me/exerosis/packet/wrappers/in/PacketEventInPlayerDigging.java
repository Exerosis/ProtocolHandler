package me.exerosis.packet.wrappers.in;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;

public class PacketEventInPlayerDigging extends PacketWrapper {
    private byte status;
    private Object blockPosition;
    private byte face;

    public PacketEventInPlayerDigging(Object packet, PacketPlayer player) {
        super(packet, player);
        status = Reflect.Field(packet, byte.class, "a").getValue();
        blockPosition = Reflect.Field(packet, Object.class, "b").getValue();
        face = Reflect.Field(packet, byte.class, "c").getValue();
    }

    public void setLocation(Object location) {
        this.blockPosition = location;
        Reflect.Field(super.getPacket(), Object.class, "b").setValue(location);
    }

    public Object getBlockPosition() {
        return blockPosition;
    }

    public byte getFace() {
        return face;
    }

    public void setFace(byte face) {
        this.face = face;
        Reflect.Field(super.getPacket(), byte.class, "c").setValue(face);
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
        Reflect.Field(super.getPacket(), byte.class, "a").setValue(status);
    }
}
