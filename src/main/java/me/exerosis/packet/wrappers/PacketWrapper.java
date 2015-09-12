package me.exerosis.packet.wrappers;

public abstract class PacketWrapper {
    private Object packet;

    public PacketWrapper(Object packet) {
        this.packet = packet;
    }

    public Object getPacket() {
        return packet;
    }

    public void setPacket(Object packet) {
        this.packet = packet;
    }
}