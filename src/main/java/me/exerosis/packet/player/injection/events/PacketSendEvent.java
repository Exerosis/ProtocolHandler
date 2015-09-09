package me.exerosis.packet.player.injection.events;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;

/**
 * Created by The Exerosis on 8/12/2015.
 */
public class PacketSendEvent extends PacketEvent {
    public PacketSendEvent(Object packet, PacketPlayer player) {
        super(packet, player);
    }
}