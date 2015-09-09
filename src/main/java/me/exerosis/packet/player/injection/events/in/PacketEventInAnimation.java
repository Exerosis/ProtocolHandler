package me.exerosis.packet.player.injection.events.in;

import me.exerosis.packet.player.injection.events.PacketEvent;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;

public class PacketEventInAnimation extends PacketEvent {

    public PacketEventInAnimation(Object packet, PacketPlayer player) {
        super(packet, player);
    }
}
