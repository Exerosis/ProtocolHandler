package me.exerosis.packet.event.bukkit;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;

public class PacketSentEvent extends PacketEvent {
    public PacketSentEvent(Object packet, PacketPlayer player) {
        super(packet, player);
    }
}