package me.exerosis.packet.wrappers.in;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;
import net.minecraft.server.v1_8_R1.PacketPlayInArmAnimation;

public class PacketEventInPlayerPosition extends PacketWrapper {

    public PacketEventInPlayerPosition(PacketPlayInArmAnimation packet, PacketPlayer player) {
        super(packet, player);
    }
}
