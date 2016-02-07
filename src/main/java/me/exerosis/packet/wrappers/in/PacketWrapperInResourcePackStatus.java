package me.exerosis.packet.wrappers.in;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.utils.packet.enums.ResourcePackStatus;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;

public class PacketWrapperInResourcePackStatus extends PacketWrapper {

    public PacketWrapperInResourcePackStatus(Object packet) {
        super(packet);
    }

    public PacketWrapperInResourcePackStatus(String hash, ResourcePackStatus status) {
        super(PacketPlay.In.ResourcePackStatus(hash, status));
    }

    public String getHash() {
        return Reflect.Field(getPacket(), String.class, 0).getValue();
    }

    public void setHash(String hash) {
        Reflect.Field(getPacket(), String.class, 0).setValue(hash);
    }

    public ResourcePackStatus getStatus() {
        return ResourcePackStatus.fromNMS(Reflect.Field(getPacket(), Object.class, 1).getValue());
    }

    public void setStatus(ResourcePackStatus status) {
        Reflect.Field(getPacket(), Object.class, 1).setValue(status.ordinal());
    }
}