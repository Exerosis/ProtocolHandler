package me.exerosis.packet.utils.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Circle {
    private Vector _midpoint = new Vector();
    private double _radius = 10;
    private boolean _hollow = true;
    private boolean _sphere = false;
    private boolean _layered = false;
    private double _yMod = 0;

    protected Circle(Builder builder) {
        _midpoint = builder._midpoint;
        _radius = builder._radius;
        _hollow = builder._hollow;
        _sphere = builder._sphere;
        _layered = builder._layered;
        _yMod = builder._yMod;
    }

    public static List<Set<Location>> layeredCircle(Location middle, int radius) {

        int y = middle.getBlockY();

        List<Set<Location>> locations = new ArrayList<>(radius);

        int[] radiiSquard = new int[radius];

        for (int i = 1; i <= radius; i++) {

            radiiSquard[i - 1] = (i * i) - (i);
            locations.add(new HashSet<>());

        }

        for (int x = -radius; x < radius; x++) {

            for (int z = -radius; z < radius; z++) {

                int num = (x * x) + (z * z);

                for (int i = 0; i < radius; i++) {
                    int amount = i + 1;
                    if (x < -amount || x > amount || z < -amount || z > amount || num >= radiiSquard[i])
                        continue;

                    locations.get(i).add(new Location(middle.getWorld(), middle.getBlockX() + x, y, middle.getBlockZ() + z));

                    break;
                }
            }
        }
        return locations;
    }

    public Vector getPointInCircle(double angle, boolean radians) {
        double pointAngle = radians ? angle : Math.toRadians(angle);
        double x = _radius * Math.cos(pointAngle) + _midpoint.getX();
        double z = _radius * Math.sin(pointAngle) + _midpoint.getZ();
        return new Vector(x, _midpoint.getY(), z);
    }

    public List<Set<Vector>> getLocations() {
        List<Set<Vector>> locations = new ArrayList<>((int) _radius);
        if (!_layered) {
            Set<Vector> locationSet = new HashSet<>();
            int cx = _midpoint.getBlockX();
            int cy = _midpoint.getBlockY();
            int cz = _midpoint.getBlockZ();
            for (double x = cx - _radius; x <= cx + _radius; x++)
                for (double z = cz - _radius; z <= cz + _radius; z++)
                    for (double y = (_sphere ? cy - _radius : cy); y < cy + _radius; y++) {
                        double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (_sphere ? (cy - y) * (cy - y) : 0);
                        if (dist < _radius * _radius && !(_hollow && dist < (_radius - 1) * (_radius - 1)))
                            locationSet.add(new Vector(x, y + _yMod, z));
                    }
            locations.add(locationSet);
            return locations;
        }

        int y = _midpoint.getBlockY();
        int[] radiiSquard = new int[(int) _radius];

        for (int i = 1; i <= _radius; i++) {
            radiiSquard[i - 1] = (i * i) - (i);
            locations.add(new HashSet<>());
        }

        for (double x = -_radius; x < _radius; x++) {

            for (double z = -_radius; z < _radius; z++) {

                double num = (x * x) + (z * z);

                for (int i = 0; i < _radius; i++) {
                    int amount = i + 1;
                    if (x < -amount || x > amount || z < -amount || z > amount || num >= radiiSquard[i])
                        continue;
                    locations.get(i).add(new Vector(_midpoint.getBlockX() + x, y, _midpoint.getBlockZ() + z));
                    break;
                }
            }
        }
        return locations;
    }

    //Getters and setters
    public Vector getMidpoint() {
        return _midpoint;
    }

    public double getRadius() {
        return _radius;
    }

    public double getyMod() {
        return _yMod;
    }

    public static class Builder {
        protected Vector _midpoint;
        protected double _radius;
        protected boolean _hollow;
        protected boolean _sphere;
        protected boolean _layered;
        protected double _yMod;

        public Builder(Location midpoint) {
            this(midpoint.toVector());
        }

        public Builder(Vector midpoint) {
            _midpoint = midpoint;
        }

        public Builder radius(double radius) {
            _radius = radius;
            return this;
        }

        public Builder hollow(boolean hollow) {
            _hollow = hollow;
            return this;
        }

        public Builder sphere(boolean sphere) {
            _sphere = sphere;
            return this;
        }

        public Builder layered(boolean layered) {
            _layered = layered;
            return this;
        }

        public Builder yMod(double yMod) {
            _yMod = yMod;
            return this;
        }

        public Circle build() {
            return new Circle(this);
        }
    }

}
