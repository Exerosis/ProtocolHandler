package me.exerosis.packet.event;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;

public class PacketSentEvent <T extends PacketWrapper> extends PacketEvent {
    public PacketSentEvent(T wrapper, PacketPlayer player) {
        super(wrapper, player);
    }
}