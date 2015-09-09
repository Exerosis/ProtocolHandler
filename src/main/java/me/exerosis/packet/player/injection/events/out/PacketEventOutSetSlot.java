package me.exerosis.packet.player.injection.events.out;

import me.exerosis.packet.player.injection.events.PacketEvent;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.utils.packet.PacketUtil;
import me.exerosis.reflection.Reflect;
import org.bukkit.inventory.ItemStack;

public class PacketEventOutSetSlot extends PacketEvent {

    public PacketEventOutSetSlot(Object packet, PacketPlayer player) {
        super(packet, player);
    }

    public int getWindowID() {
        return Reflect.Field(getPacket(), int.class, 0).getValue();
    }

    public void setWindowID(int windowID) {
        Reflect.Field(super.getPacket(), int.class, 0).setValue(windowID);
    }

    public int getSlot() {
        return Reflect.Field(getPacket(), int.class, 1).getValue();
    }

    public void setSlot(int slot) {
        Reflect.Field(super.getPacket(), int.class, 1).setValue(slot);
    }

    public ItemStack getItem() {
        return PacketUtil.BukkitItemFromNMS(Reflect.Field(getPacket(), Object.class, 0).getValue());
    }

    public void setItem(ItemStack item) {
        Reflect.Field(super.getPacket(), Object.class, 0).setValue(item);
    }
}