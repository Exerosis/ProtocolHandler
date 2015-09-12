package me.exerosis.packet.wrappers.in;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;

public final class PacketWrapperInChat extends PacketWrapper {

    public PacketWrapperInChat(Object packet) {
        super(packet);
    }

    public PacketWrapperInChat(String message) {
        super(PacketPlay.In.Chat(message));
    }

    public String getMessage() {
        return Reflect.Field(getPacket(), String.class, 0).getValue();
    }

    public void setMessage(String message) {
        Reflect.Field(getPacket(), String.class, 0).setValue(message);
    }
}