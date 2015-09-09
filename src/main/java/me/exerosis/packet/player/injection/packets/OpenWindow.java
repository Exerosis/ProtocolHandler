package me.exerosis.packet.player.injection.packets;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.player.injection.packet.player.display.DisplayUtils;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutOpenWindow;
import org.bukkit.ChatColor;

public class OpenWindow implements PreconPacket {
    private Object packet;

    public OpenWindow(int windowID, String type, int size, String name) {
        IChatBaseComponent chatCompoent = DisplayUtils.serializeText(name, ChatColor.BLACK);
        packet = new PacketPlayOutOpenWindow(size, type, chatCompoent, windowID);
    }

    public void send(PacketPlayer player) {
        player.sendPacket(packet);
    }
}
