package me.exerosis.packet.wrappers.entity.out.move;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.utils.packet.PacketUtil;
import me.exerosis.reflection.Reflect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class PacketWrapperOutRelEntityMoveLook extends PacketWrapperOutRelEntityMove {
    /*
    double yaw
    double pitch
     */
    public PacketWrapperOutRelEntityMoveLook(Object packet) {
        super(packet);
    }

    public PacketWrapperOutRelEntityMoveLook(int id, double x, double y, double z, double yaw, double pitch, boolean onGround) {
        super(PacketPlay.Out.RelEntityMoveLook(id, x, y, z, yaw, pitch, onGround));
    }

    public PacketWrapperOutRelEntityMoveLook(int id, Vector location, double yaw, double pitch, boolean onGround) {
        super(PacketPlay.Out.RelEntityMoveLook(id, location.getX(), location.getY(), location.getZ(), yaw, pitch, onGround));
    }

    public PacketWrapperOutRelEntityMoveLook(int id, Location location, boolean onGround) {
        super(PacketPlay.Out.RelEntityMoveLook(id, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), onGround));
    }

    public double getYaw() {
        return PacketUtil.fromNMSAngleForm(Reflect.Field(getPacket(), byte.class, 3).getValue());
    }

    public void setYaw(double yaw) {
        Reflect.Field(getPacket(), byte.class, 3).setValue(PacketUtil.toNMSAngleForm(yaw));
    }

    public double getPitch() {
        return PacketUtil.fromNMSAngleForm(Reflect.Field(getPacket(), byte.class, 4).getValue());
    }

    public void setPitch(double pitch) {
        Reflect.Field(getPacket(), byte.class, 4).setValue(PacketUtil.toNMSAngleForm(pitch));
    }
}