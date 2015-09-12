package me.exerosis.packet.wrappers.in;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;

public class PacketWrapperInHeldItemSlot extends PacketWrapper {

    public PacketWrapperInHeldItemSlot(Object packet) {
        super(packet);
    }

    public PacketWrapperInHeldItemSlot(int slot) {
        super(PacketPlay.In.HeldItemSlot(slot));
    }

    public short getSlot() {
        return Reflect.Field(getPacket(), short.class, 0).getValue();
    }

    public void setSlot(short slot) {
        Reflect.Field(getPacket(), short.class, 0).setValue(slot);
    }
}