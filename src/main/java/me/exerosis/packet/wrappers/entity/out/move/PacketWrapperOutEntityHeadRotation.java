package me.exerosis.packet.wrappers.entity.out.move;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.utils.packet.PacketUtil;
import me.exerosis.packet.wrappers.entity.PacketWrapperEntity;
import me.exerosis.reflection.Reflect;

public class PacketWrapperOutEntityHeadRotation extends PacketWrapperEntity {
    /*
    double yaw
    double pitch
     */
    public PacketWrapperOutEntityHeadRotation(Object packet) {
        super(packet);
    }

    public PacketWrapperOutEntityHeadRotation(int entityID, double rotation) {
        super(PacketPlay.Out.EntityHeadRotation(entityID, rotation));
    }

    public double getRotation() {
        return PacketUtil.fromNMSAngleForm(Reflect.Field(getPacket(), byte.class, 0).getValue());
    }

    public void setRotation(double rotation) {
        Reflect.Field(getPacket(), byte.class, 0).setValue(PacketUtil.toNMSAngleForm(rotation));
    }
}