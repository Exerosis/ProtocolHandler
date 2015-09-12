package me.exerosis.packet.utils.packet;

import me.exerosis.reflection.Reflect;
import me.exerosis.reflection.ReflectClass;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

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

    public static Object NMSItemFromID(int id) {
        return Reflect.Class("{nms}.Item").getMethod("getById", int.class).call(id);
    }

    public static Object toBlockLocation(Location location) {
        return toBlockLocation(location.toVector());
    }

    public static Object toBlockLocation(Vector location) {
        ReflectClass<Object> clazz = Reflect.Class("{nms}.BlockPosition");
        return clazz == null ? null : clazz.newInstance(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static Location fromBlockPosition(Object object, World world) {
        return fromBlockPosition(object).toLocation(world);
    }

    public static Vector fromBlockPosition(Object object) {
        ReflectClass<Object> clazz = Reflect.Class(object);
        int x = clazz.getField(int.class, 0).getValue();
        int y = clazz.getField(int.class, 1).getValue();
        int z = clazz.getField(int.class, 2).getValue();
        return new Vector(x, y, z);
    }

    public static Object toVec3D(Vector vector){
       return Reflect.Class("{nms}Vec3D").newInstance(vector.getX(), vector.getY(), vector.getZ());
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