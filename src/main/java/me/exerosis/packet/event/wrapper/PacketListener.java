package me.exerosis.packet.event.wrapper;

import me.exerosis.packet.event.PacketEvent;
import me.exerosis.packet.wrappers.PacketWrapper;

public interface PacketListener<T extends PacketWrapper> {
    void onPacket(PacketEvent<T> event);
}