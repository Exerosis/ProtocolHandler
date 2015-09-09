package me.exerosis.packet.wrappers.entity;

import me.exerosis.reflection.Reflect;

public abstract class PacketWrapperEntity {
    private Object packet;

    protected PacketWrapperEntity(int id, Object packet) {
        this.packet = packet;
        setEntityID(id);
    }

    protected PacketWrapperEntity(Object packet) {
        this.packet = packet;
    }

    public double getEntityID() {
        return Reflect.Field(getPacket(), int.class, 0).getValue();
    }

    public void setEntityID(int id) {
        Reflect.Field(getPacket(), int.class, 0).setValue(id);
    }

    public Object getPacket() {
        return packet;
    }

    public void setPacket(Object packet) {
        this.packet = packet;
    }
}
