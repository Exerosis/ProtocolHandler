package me.exerosis.packet.player.injection.events.out.entity;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.utils.packet.PacketUtil;
import me.exerosis.reflection.Reflect;
import org.bukkit.inventory.ItemStack;

public class PacketEventOutEntityEquipment extends PacketEventOutEntity {
    /*
    int slot
    ItemStack stack
     */
    public PacketEventOutEntityEquipment(Object packet, PacketPlayer player) {
        super(packet, player);
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