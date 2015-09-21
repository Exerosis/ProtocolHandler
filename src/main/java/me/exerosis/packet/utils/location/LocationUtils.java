package me.exerosis.packet.utils.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocationUtils {
    private LocationUtils() {
    }

    public static Location roundLocation(Location location) {
        double x = Math.floor(location.getX()) + 0.5;
        double y = location.getY();
        double z = Math.floor(location.getZ()) + 0.5;
        float yaw = Math.round((location.getYaw()) / 10) * 10;
        float pitch = Math.round((location.getPitch()) / 10) * 10;
        return new Location(location.getWorld(), x, y, z, yaw, pitch);
    }

    public static String toAbsoluteString(Location location) {
        String world = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        String dataString = world + ", " + x + ", " + y + ", " + z;

        if (yaw != 0 && pitch != 0)
            dataString += ", " + yaw + ", " + pitch;

        return dataString;
    }

    public static String toRoundedString(Location location) {
        location = roundLocation(location);
        return toAbsoluteString(location);
    }

    public static Location fromString(String dataString) {
        dataString = dataString.replace(" ", "");
        String[] data = dataString.split(",");
        if (!(data.length == 4 || data.length == 6))
            return null;

        World world = data[0].equals("") ? null : Bukkit.getWorld(data[0]);

        double x = Double.parseDouble(data[1]);
        double y = Double.parseDouble(data[2]);
        double z = Double.parseDouble(data[3]);
        if (data.length == 6) {
            float yaw = Float.parseFloat(data[4]);
            float pitch = Float.parseFloat(data[5]);
            return new Location(world, x, y, z, yaw, pitch);
        }
        return new Location(world, x, y, z);
    }

    public static Location fromString(String dataString, World world) {
        dataString = dataString.replace(" ", "");
        String[] data = dataString.split(",");
        if (!(data.length == 3 || data.length == 5))
            return null;

        double x = Double.parseDouble(data[0]);
        double y = Double.parseDouble(data[1]);
        double z = Double.parseDouble(data[2]);
        if (data.length == 5) {
            float yaw = Float.parseFloat(data[3]);
            float pitch = Float.parseFloat(data[4]);
            return new Location(world, x, y, z, yaw, pitch);
        }
        return new Location(world, x, y, z);
    }

    public static String vectorToString(Vector vector){
        return vector.getX() + ", " + vector.getY() + ", " + vector.getZ();
    }

    public static Vector vectorFromString(String dataString) {
        dataString = dataString.replace(" ", "");
        String[] data = dataString.split(",");
        if (data.length != 3)
            return null;

        double x = Double.parseDouble(data[0]);
        double y = Double.parseDouble(data[1]);
        double z = Double.parseDouble(data[2]);
        return new Vector(x, y, z);
    }

    public static List<Location> circle(Location location, int radius, int height, boolean hollow, boolean sphere, int plusY) {
        List<Location> circleBlocks = new ArrayList<>();
        int cx = location.getBlockX();
        int cy = location.getBlockY();
        int cz = location.getBlockZ();
        for (int x = cx - radius; x <= cx + radius; x++)
            for (int z = cz - radius; z <= cz + radius; z++)
                for (int y = (sphere ? cy - radius : cy); y < (sphere ? cy + radius : cy + height); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))) {
                        Location l = new Location(location.getWorld(), x, y + plusY, z);
                        circleBlocks.add(l);
                    }
                }

        return circleBlocks;
    }

    public static List<Set<Location>> layeredCircle(Location middle, int radius) {

        int y = middle.getBlockY();

        List<Set<Location>> locs = new ArrayList<>(radius);

        int[] radiiSquard = new int[radius];

        for (int i = 1; i <= radius; i++) {

            radiiSquard[i - 1] = (i * i) - (i);
            locs.add(new HashSet<>());

        }

        for (int x = -radius; x < radius; x++) {

            for (int z = -radius; z < radius; z++) {

                int num = (x * x) + (z * z);

                for (int i = 0; i < radius; i++) {
                    int amount = i + 1;
                    if (x < -amount || x > amount || z < -amount || z > amount || num >= radiiSquard[i])
                        continue;

                    locs.get(i).add(new Location(middle.getWorld(), middle.getBlockX() + x, y, middle.getBlockZ() + z));

                    break;
                }
            }
        }
        return locs;
    }

    public static LivingEntity getLivingTarget(Player player, int range, boolean xRay) {
        List<Entity> nearbyEntities = player.getNearbyEntities(range, range, range);

        int i = 0;
        while (i < nearbyEntities.size()) {
            if (nearbyEntities.get(i) instanceof LivingEntity)
                i++;
            else
                nearbyEntities.remove(i);
        }
        LocationIterator locationIterator = new LocationIterator(player, range);

        while (locationIterator.hasNext()) {
            Location location = locationIterator.next();
            if (!xRay)
                if (location.getBlock().getType().isSolid() || !location.getBlock().getType().isTransparent())
                    return null;
            double blockX = location.getX();
            double blockY = location.getY();
            double blockZ = location.getZ();

            for (Entity entity : nearbyEntities) {
                double x = entity.getLocation().getX();
                double y = entity.getLocation().getY();
                double z = entity.getLocation().getZ();

                if (blockX - .75 <= x && x <= blockX + 1.75)
                    if (blockZ - .75 <= z && z <= blockZ + 1.75)
                        if (blockY - 1 <= y && y <= blockY + 2.5)
                            return (LivingEntity) entity;
            }
        }
        return null;
    }

    public static Location getTarget(Player player, int range) {
        LocationIterator locationIterator = new LocationIterator(player, range);

        while (locationIterator.hasNext()) {
            Location location = locationIterator.next();
            if (location.getBlock().getType().isSolid() || !location.getBlock().getType().isTransparent())
                return location;
        }
        return null;
    }
}
