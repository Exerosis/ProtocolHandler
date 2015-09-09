package me.exerosis.packet.player.injection.events.in;

import me.exerosis.packet.player.injection.events.PacketEvent;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;

public class PacketEventInHeldItemChange extends PacketEvent {
    private short slot;

    public PacketEventInHeldItemChange(Object packet, PacketPlayer player) {
        super(packet, player);
        slot = Reflect.Field(packet, short.class, "a").getValue();
    }

    public short getSlot() {
        return slot;
    }

    public void setSlot(short slot) {
        Reflect.Field(super.getPacket(), short.class, "a").setValue(slot);
        this.slot = slot;
    }
}
