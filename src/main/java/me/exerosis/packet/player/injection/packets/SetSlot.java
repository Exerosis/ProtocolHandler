package me.exerosis.packet.player.injection.packets;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;
import org.bukkit.inventory.ItemStack;

public class SetSlot implements PreconPacket {
    private Object packet;

    public SetSlot(int slotID, int windowID, ItemStack itemStack) {
        Object item = Reflect.Class("{cb}.inventory.CraftItemStack").getMethod("asNMSCopy").call(itemStack);
        packet = Reflect.Class("{nms}.PacketPlayOutSetSlot").newInstance(slotID, windowID, item);
    }

    public void send(PacketPlayer player) {
        player.sendPacket(packet);
    }
}
