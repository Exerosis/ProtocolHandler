package me.exerosis.packet.player.injection.events.in;

import me.exerosis.packet.player.injection.events.PacketEvent;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;
import net.minecraft.server.v1_8_R1.PacketPlayInCloseWindow;

public final class PacketEventInWindowClose extends PacketEvent {
    private int windowId;

    public PacketEventInWindowClose(PacketPlayInCloseWindow packet, PacketPlayer player) {
        super(packet, player);
        windowId = Reflect.Field(packet, int.class, "id").getValue();
    }

    public int getWindowId() {
        return windowId;
    }

    public void setWindowId(int windowId) {
        this.windowId = windowId;
        Reflect.Field(super.getPacket(), int.class, "id").setValue(windowId);
    }
}
