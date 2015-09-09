package me.exerosis.packet.utils.location;

import me.exerosis.packet.utils.packet.PacketUtil;
import me.exerosis.reflection.Reflect;
import me.exerosis.reflection.ReflectClass;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class AdvancedLocation extends Location {

    public AdvancedLocation(World world, double x, double y, double z, float yaw, float pitch) {
        super(world, x, y, z);
    }

    public AdvancedLocation(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public AdvancedLocation(Location location) {
        super(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public AdvancedLocation(Vector location) {
        super(null, location.getX(), location.getY(), location.getZ(), 0, 0);
    }

    public static float getMotionYaw(Vector motion) {
        double dx = motion.getX();
        double dz = motion.getZ();
        double yaw = 0;

        if (dx != 0) {
            if (dx < 0)
                yaw = 1.5 * Math.PI;
            else
                yaw = 0.5 * Math.PI;
            yaw -= Math.atan(dz / dx);
        } else if (dz < 0)
            yaw = Math.PI;
        return (float) (-yaw * 180 / Math.PI - 90);
    }

    public static float getMotionPitch(Vector motion) {
        double dxz = Math.sqrt(motion.getX() * motion.getX() + motion.getZ() * motion.getZ());
        return (float) Math.cos(motion.getY() / dxz);
    }

    public static BlockFace getMotionDirection(Vector motion) {
        return getCardinalDirection(getMotionYaw(motion));
    }

    public static String getComparableAxisName(Location location) {
        switch (getCardinalDirection(location)) {
            case NORTH:
                return "-Z";
            case SOUTH:
                return "+Z";
            case EAST:
                return "+X";
            case WEST:
                return "-X";
            default:
                return "-Z";
        }
    }

    public static BlockFace getCardinalDirection(Location location) {
        return getCardinalDirection(location.getYaw());
    }

    public static BlockFace getCardinalDirection(float yaw) {
        float direction = Math.round(yaw / 90);
        if (direction == -4 || direction == 0 || direction == 4)
            return BlockFace.SOUTH;
        if (direction == -1 || direction == 3)
            return BlockFace.EAST;
        if (direction == -2 || direction == 2)
            return BlockFace.NORTH;
        if (direction == -3 || direction == 1)
            return BlockFace.WEST;
        return null;
    }

    public String getComparableAxisName() {
        switch (getCardinalDirection()) {
            case NORTH:
                return "-Z";
            case SOUTH:
                return "+Z";
            case EAST:
                return "+X";
            case WEST:
                return "-X";
            default:
                return "-Z";
        }
    }

    public BlockFace getCardinalDirection() {
        return getCardinalDirection(this.getYaw());
    }

    @SuppressWarnings("deprecation")
    public boolean isSlab() {
        int id = getBlock().getTypeId();
        if (id == 44 || id == 126) {
            byte data = getBlock().getData();
            if (data >= 0 && data <= 7)
                return true;
        }
        return false;
    }

    public byte getBedByte() {
        switch (getCardinalDirection(this)) {
            case NORTH:
                return 2;
            case SOUTH:
                return 0;
            case EAST:
                return 3;
            case WEST:
                return 1;
            default:
                return 0;
        }
    }

    public int getPointX() {
        return PacketUtil.toPointForm(getX());
    }

    public int getPointY() {
        return PacketUtil.toPointForm(getY());
    }

    public int getPointZ() {
        return PacketUtil.toPointForm(getZ());
    }

    public Object toBlockPosition() {
        ReflectClass<Object> clazz = Reflect.Class("{nms}.BlockPosition");
        return clazz == null ? null : clazz.newInstance(getBlockX(), getBlockY(), getBlockZ());
    }
}