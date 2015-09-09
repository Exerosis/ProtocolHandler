package me.exerosis.packet.player.injection.events.out;

import me.exerosis.packet.player.injection.events.PacketEvent;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;
import org.bukkit.inventory.ItemStack;

public class PacketEventOutEntityEquipment extends PacketEvent {
    private int EID;
    private int slot;
    private ItemStack item;

    public PacketEventOutEntityEquipment(Object packet, PacketPlayer player) {
        super(packet, player);
        Object nmsItem = Reflect.Field("c", packet, Object.class).getValue();

        EID = Reflect.Field("a", packet, int.class).getValue();
        slot = Reflect.Field("b", packet, int.class).getValue();
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
