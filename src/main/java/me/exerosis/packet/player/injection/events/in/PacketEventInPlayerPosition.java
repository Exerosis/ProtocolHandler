package me.exerosis.packet.player.injection.events.in;

import me.exerosis.packet.player.injection.events.PacketEvent;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import net.minecraft.server.v1_8_R1.PacketPlayInArmAnimation;

public class PacketEventInPlayerPosition extends PacketEvent {

    public PacketEventInPlayerPosition(PacketPlayInArmAnimation packet, PacketPlayer player) {
        super(packet, player);
    }
}
