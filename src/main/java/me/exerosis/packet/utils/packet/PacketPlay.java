package me.exerosis.packet.utils.packet;

import me.exerosis.packet.utils.location.AdvancedLocation;
import me.exerosis.reflection.Reflect;
import me.exerosis.reflection.ReflectClass;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * Created by The Exerosis on 8/12/2015.
 */
@SuppressWarnings("deprecation")
public final class PacketPlay {
    private PacketPlay() {
    }

    public static class Out {
        private Out() {
        }

        public static Object EntityEquipment(int id, int slot, ItemStack itemStack) {
            Object item = PacketUtil.NMSItemFromBukkit(itemStack);
            ReflectClass<Object> clazz = Reflect.Class("{nms}.PacketPlayOutEntityEquipment");
            return clazz == null ? null : clazz.newInstance(id, slot, item);
        }

        public static Object NamedEntitySpawn(Object entity) {
            ReflectClass<Object> clazz = Reflect.Class("{nms}.PacketPlayOutEntityEquipment");
            return clazz == null ? null : clazz.newInstance(entity);
        }

        public static Object Bed(int id, Location location) {
            return Bed(id, location.getX(), location.getY(), location.getZ());
        }

        public static Object Bed(int id, double x, double y, double z) {
            ReflectClass<Object> packetClass = Reflect.Class("{nms}.PacketPlayOutBed");
            if (packetClass == null)
                return null;

            Object packet = packetClass.newInstance();
            Object advancedLocation = new AdvancedLocation(null, x, y, z).toBlockPosition();

            packetClass.getField(int.class, 0).setValue(id);
            packetClass.getField(Object.class, 1).setValue(advancedLocation);
            return packet;
        }

        public static Object EntityTeleport(int id, double x, double y, double z, float yaw, float pitch, boolean isOnGround) {
            return EntityTeleport(id, new Location(null, x, y, z, yaw, pitch), isOnGround);
        }

        public static Object EntityTeleport(int id, Location location, boolean isOnGround) {
            ReflectClass<Object> packetClass = Reflect.Class("{nms}.PacketPlayOutEntityTeleport");
            if (packetClass != null)
                return packetClass.newInstance(id, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), isOnGround);
            return null;
        }

        public static Object RelEntityMove(int id, double x, double y, double z, boolean onGround) {
            ReflectClass<Object> packetClass = Reflect.Class("{nms}.PacketPlayOutRelEntityMove");
            return packetClass.newInstance(id, PacketUtil.toPointForm(x), PacketUtil.toPointForm(y), PacketUtil.toPointForm(z), onGround);
        }

        public static Object RelEntityMove(int id, Vector location, boolean onGround) {
            return RelEntityMove(id, location.getX(), location.getY(), location.getZ(), onGround);
        }

        public static Object RelEntityMove(int id, Location location, boolean onGround) {
            return RelEntityMove(id, location.getX(), location.getY(), location.getZ(), onGround);
        }

        public static Object RelEntityMoveLook(int id, double x, double y, double z, double yaw, double pitch, boolean onGround) {
            ReflectClass<Object> packetClass = Reflect.Class("{nms}.PacketPlayOutRelEntityMoveLook");
            byte pointX = PacketUtil.toPointForm(x);
            byte pointY = PacketUtil.toPointForm(y);
            byte pointZ = PacketUtil.toPointForm(z);
            byte nmsYaw = PacketUtil.toNMSAngleForm(yaw);
            byte nmsPitch = PacketUtil.toNMSAngleForm(pitch);
            return packetClass.newInstance(id, pointX, pointY, pointZ, nmsYaw, nmsPitch, onGround);
        }

        public static Object RelEntityMoveLook(int id, Location location, boolean onGround) {
            return RelEntityMoveLook(id, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), onGround);
        }

        public static Object EntityHeadRotation(int id, double rotation) {
            ReflectClass<Object> packetClass = Reflect.Class("{nms}.PacketPlayOutEntityHeadRotation");
            packetClass.newInstance();
            packetClass.getField(int.class, 0).setValue(id);
            packetClass.getField(byte.class, PacketUtil.toNMSAngleForm(rotation));
            return packetClass.getInstance();
        }

        public static Object EntityLook(int id, double yaw, double pitch, boolean onGround) {
            ReflectClass<Object> packetClass = Reflect.Class("{nms}.PacketPlayOutRelEntityLook");
            byte nmsYaw = PacketUtil.toNMSAngleForm(yaw);
            byte nmsPitch = PacketUtil.toNMSAngleForm(pitch);
            return packetClass.newInstance(id, nmsYaw, nmsPitch, onGround);
        }

        public static Object EntityEffect(int id, int effectID, int duration, int amplifier, boolean ambient) {
            ReflectClass<Object> packetClass = Reflect.Class("{nms}.PacketPlayOutEntityEffect");
            packetClass.newInstance();
            packetClass.getField(int.class, 0).setValue(id);
            packetClass.getField(byte.class, 0).setValueCast(effectID & 255);
            packetClass.getField(byte.class, 1).setValueCast(amplifier & 255);
            packetClass.getField(int.class, 1).setValue(Math.min(duration, 32767));
            packetClass.getField(byte.class, 2).setValueCast(ambient ? 1 : 0);
            return packetClass.getInstance();
        }

        public static Object EntityEffect(int id, Object mobEffect) {
            return Reflect.Class("{nms}.PacketPlayOutEntityEffect").newInstance(id, mobEffect);
        }

        public static Object EntityEffect(int id, PotionEffect effect) {
            return EntityEffect(id, effect.getType().getId(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient());
        }

        public static Object EntityEffect(int id, PotionEffectType type, int duration, int amplifier, boolean ambient) {
            return EntityEffect(id, type.getId(), duration, amplifier, ambient);
        }
    }
}