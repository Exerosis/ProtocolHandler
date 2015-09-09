package me.exerosis.packet.wrappers.entity.out;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.utils.packet.PacketUtil;
import me.exerosis.packet.wrappers.entity.PacketWrapperEntity;
import me.exerosis.reflection.Reflect;
import org.bukkit.inventory.ItemStack;

public class PacketWrapperOutEntityEquipment extends PacketWrapperEntity {
    /*
    int slot
    ItemStack stack
    */
    public PacketWrapperOutEntityEquipment(int EID, int slot, ItemStack itemStack) {
        super(PacketPlay.Out.EntityEquipment(EID, slot, itemStack));
    }

    public PacketWrapperOutEntityEquipment(Object packet) {
        super(packet);
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
        Reflect.Field(getPacket(), Object.class, 0).setValue(PacketUtil.NMSItemFromBukkit(item));
    }
}