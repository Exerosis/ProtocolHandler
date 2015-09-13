package me.exerosis.packet.event.bukkit;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;

public class PacketReceiveEvent extends PacketEvent {
    public PacketReceiveEvent(Object packet, PacketPlayer player) {
        super(packet, player);
    }
}