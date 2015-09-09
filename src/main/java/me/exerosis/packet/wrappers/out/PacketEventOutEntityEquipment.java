package me.exerosis.packet.wrappers.out;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;
import me.exerosis.packet.wrappers.PacketWrapper;
import org.bukkit.inventory.ItemStack;

public class PacketEventOutEntityEquipment extends PacketWrapper {
    private int EID;
    private int slot;
    private ItemStack item;

    public PacketEventOutEntityEquipment(Object packet, PacketPlayer player) {
        super(packet, player);
        Object nmsItem = Reflect.Field(packet, Object.class, "c").getValue();

        EID = Reflect.Field(packet, int.class, "a").getValue();
        slot = Reflect.Field(packet, int.class, "b").getValue();
        item = (ItemStack) Reflect.Class("{cb}.inventory.CraftItemStack").getMethod("asBukkitCopy").call(nmsItem);
    }

    public int getEID() {
        return EID;
    }

    public void setEID(int EID) {
        this.EID = EID;
        Reflect.Field("a", super.getPacket(), int.class).setValue(EID);
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
        Reflect.Field("b", super.getPacket(), int.class).setValue(slot);
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
        Reflect.Field("c", super.getPacket(), Object.class).setValue(item);
    }
}
