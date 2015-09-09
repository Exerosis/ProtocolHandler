package me.exerosis.packet.wrappers.entity.out.move;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.utils.packet.PacketUtil;
import me.exerosis.packet.wrappers.entity.PacketWrapperEntity;
import me.exerosis.reflection.Reflect;

public class PacketWrapperOutEntityLook extends PacketWrapperEntity {
    /*
    double yaw
    double pitch
     */
    public PacketWrapperOutEntityLook(Object packet) {
        super(packet);
    }

    public PacketWrapperOutEntityLook(int entityID, double yaw, double pitch, boolean onGround) {
        super(PacketPlay.Out.EntityLook(entityID, yaw, pitch, onGround));
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