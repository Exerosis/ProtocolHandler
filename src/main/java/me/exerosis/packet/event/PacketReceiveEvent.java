package me.exerosis.packet.event;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;

public class PacketReceiveEvent <T extends PacketWrapper> extends PacketEvent {
    public PacketReceiveEvent(T wrapper, PacketPlayer player) {
        super(wrapper, player);
    }
}