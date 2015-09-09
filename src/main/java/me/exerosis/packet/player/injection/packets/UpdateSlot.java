package me.exerosis.packet.player.injection.packets;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;

public class UpdateSlot implements PreconPacket {
    private int slot;

    public UpdateSlot(int slot) {
        this.slot = slot;
    }

    public void send(PacketPlayer player) {
        Object itemStack = Reflect.Class("{nms}CraftItemStack").getMethod("asNMSCopy").call(player.getPlayer().getInventory().getItem(slot));
        Reflect.Class("{nms}PacketPlayOutSetSlot").newInstance(0, slot, itemStack);
    }
}
