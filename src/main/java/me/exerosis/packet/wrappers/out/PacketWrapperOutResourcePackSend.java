package me.exerosis.packet.wrappers.out;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;

public class PacketWrapperOutResourcePackSend extends PacketWrapper {
    public PacketWrapperOutResourcePackSend(Object packet) {
        super(packet);
    }

    public PacketWrapperOutResourcePackSend(String URL, String hash) {
        super(PacketPlay.Out.ResourcePackSend(URL, hash));
    }

    public String getURL() {
        return Reflect.Field(getPacket(), String.class, 0).getValue();
    }

    public void setURL(String URL) {
        Reflect.Field(getPacket(), String.class, 0).setValue(URL);
    }

    public String getHash() {
        return Reflect.Field(getPacket(), String.class, 1).getValue();
    }

    public void setHash(String hash) {
        Reflect.Field(getPacket(), String.class, 1).setValue(hash);
    }
}