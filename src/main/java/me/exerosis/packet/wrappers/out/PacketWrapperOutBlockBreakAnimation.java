package me.exerosis.packet.wrappers.out;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.utils.packet.PacketUtil;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class PacketWrapperOutBlockBreakAnimation extends PacketWrapper {

    public PacketWrapperOutBlockBreakAnimation(Object packet) {
        super(packet);
    }

    public PacketWrapperOutBlockBreakAnimation(Object location, int stage) {
        super(PacketPlay.Out.BlockBreakAnimation(location, stage));
    }

    public PacketWrapperOutBlockBreakAnimation(Vector location, int stage) {
        super(PacketPlay.Out.BlockBreakAnimation(location, stage));
    }

    public PacketWrapperOutBlockBreakAnimation(Location location, int stage) {
        super(PacketPlay.Out.BlockBreakAnimation(location, stage));
    }

    public PacketWrapperOutBlockBreakAnimation(int x, int y, int z, int stage) {
        super(PacketPlay.Out.BlockBreakAnimation(x, y, z, stage));
    }

    public int getID() {
        return Reflect.Field(getPacket(), int.class, 0).getValue();
    }

    public void setID(int animationID) {
        Reflect.Field(getPacket(), int.class, 0).setValue(animationID);
    }

    public Object getLocation() {
        return Reflect.Field(getPacket(), Object.class, 0).getValue();
    }

    public void setLocation(Object location) {
        Reflect.Field(getPacket(), Object.class, 0).setValue(location);
    }

    public void setLocation(int x, int y, int z) {
        setLocation(PacketUtil.toBlockLocation(new Vector(x, y, z)));
    }

    public void setLocation(Location location) {
        setLocation(PacketUtil.toBlockLocation(location));
    }

    public void setLocation(Vector location) {
        setLocation(PacketUtil.toBlockLocation(location));
    }

    public int getStage() {
        return Reflect.Field(getPacket(), int.class, 1).getValue();
    }

    public void setStage(int stage) {
        Reflect.Field(getPacket(), int.class, 1).setValue(stage);
    }
}