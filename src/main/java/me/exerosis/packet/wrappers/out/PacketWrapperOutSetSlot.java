package me.exerosis.packet.wrappers.out;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.utils.packet.PacketUtil;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;
import org.bukkit.inventory.ItemStack;

public class PacketWrapperOutSetSlot extends PacketWrapper {

    public PacketWrapperOutSetSlot(Object packet) {
        super(packet);
    }

    public PacketWrapperOutSetSlot(int windowID, int slot, ItemStack item) {
        super(PacketPlay.Out.SetSlot(windowID, slot, item));
    }

    public PacketWrapperOutSetSlot(int windowID, int slot, Object item) {
        super(PacketPlay.Out.SetSlot(windowID, slot, item));
    }

    public int getWindowID() {
        return Reflect.Field(getPacket(), int.class, 0).getValue();
    }

    public void setWindowID(int windowID) {
        Reflect.Field(getPacket(), int.class, 0).setValue(windowID);
    }

    public int getSlot() {
        return Reflect.Field(getPacket(), int.class, 1).getValue();
    }

    public void setSlot(int slot) {
        Reflect.Field(getPacket(), int.class, 1).setValue(slot);
    }

    public ItemStack getItem() {
        return PacketUtil.BukkitItemFromNMS(Reflect.Field(getPacket(), Object.class, 0).getValue());
    }

    public void setItem(ItemStack item) {
        Reflect.Field(getPacket(), Object.class, 0).setValue(item);
    }
}