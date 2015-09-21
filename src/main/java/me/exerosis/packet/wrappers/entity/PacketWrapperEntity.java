package me.exerosis.packet.wrappers.entity;

import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;

public abstract class PacketWrapperEntity extends PacketWrapper {

    protected PacketWrapperEntity(int id, Object packet) {
        super(packet);
        setEntityID(id);
    }

    protected PacketWrapperEntity(Object packet) {
        super(packet);
    }

    public double getEntityID() {
        return Reflect.Field(getPacket(), int.class, 0).getValue();
    }

    public void setEntityID(int id) {
        Reflect.Field(getPacket(), int.class, 0).setValue(id);
    }
}