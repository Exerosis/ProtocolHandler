package me.exerosis.packet.player.injection.events.in;

import me.exerosis.packet.player.injection.events.PacketEvent;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;

public class PacketEventInResourcePackStatus extends PacketEvent {
    private String hash;
    private int status;

    @SuppressWarnings("rawtypes")
    public PacketEventInResourcePackStatus(Object packet, PacketPlayer player) {
        super(packet, player);
        hash = Reflect.Field(packet, String.class, "a").getValue();
        status = ((Enum) Reflect.Field(packet, Object.class, "b").getValue()).ordinal();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
        Reflect.Field(super.getPacket(), String.class, "a").setValue(hash);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        Reflect.Field(super.getPacket(), int.class, "b").setValue(status);
    }
}
