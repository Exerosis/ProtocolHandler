package me.exerosis.packet.player.injection.packets;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;

public class PromptResourcePack implements PreconPacket {
    private Object packet;

    public PromptResourcePack(String packURL) {
        packet = Reflect.Class("{nms}PacketPlayOutResourcePackSend").newInstance(packURL, "");
    }

    public void send(PacketPlayer player) {
        player.sendPacket(packet);
    }
}
