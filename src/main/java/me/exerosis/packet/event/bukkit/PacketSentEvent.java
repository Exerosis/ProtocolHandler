package me.exerosis.packet.event.bukkit;

import me.exerosis.packet.injection.PacketPlayer;

public class PacketSentEvent extends PacketEvent {
    public PacketSentEvent(Object packet, PacketPlayer player) {
        super(packet, player);
    }
}