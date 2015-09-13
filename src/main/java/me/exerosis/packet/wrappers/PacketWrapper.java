package me.exerosis.packet.wrappers;

public abstract class PacketWrapper {
    private Object packet;
    private boolean sync = true;

    public PacketWrapper(Object packet) {
        this.packet = packet;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public Object getPacket() {
        return packet;
    }

    public void setPacket(Object packet) {
        this.packet = packet;
    }
}