package me.exerosis.packet.utils.packet;

import me.exerosis.reflection.Reflect;
import me.exerosis.reflection.ReflectClass;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

/**
 * Created by The Exerosis on 8/12/2015.
 */
public final class PacketUtil {
    private PacketUtil() {
    }

    public static Object NMSItemFromBukkit(ItemStack stack) {
        ReflectClass<Object> clazz = Reflect.Class("{cb}.inventory.CraftItemStack");
        return clazz == null ? null : clazz.getMethod("asNMSCopy").call(stack);
    }

    public static ItemStack BukkitItemFromNMS(Object stack) {
        ReflectClass<Object> clazz = Reflect.Class("{cb}.inventory.CraftItemStack");
        return clazz == null ? null : (ItemStack) clazz.getMethod("asBukkitCopy").call(stack);
    }

    public static Object NMSItemFromID(int id){
        return Reflect.Class("{nms}.Item").getMethod("getById", int.class).call(id);
    }

    public static Location toBlockLocation(Location location) {
        return new Location(
                location.getWorld(),
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ());
    }

    public static double fromPointForm(byte point) {
        return (double) point / 32.0D;
    }

    public static byte toPointForm(double point) {
        return (byte) (point * 32.0D);
    }

    public static byte toNMSAngleForm(double angle) {
        return (byte) ((int) (angle * 256.0F / 360.0F));
    }

    public static double fromNMSAngleForm(byte angle) {
        return angle / 256.0F / 360.0F;
    }
}