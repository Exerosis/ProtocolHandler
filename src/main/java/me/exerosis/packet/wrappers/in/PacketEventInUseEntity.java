package me.exerosis.packet.wrappers.in;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;
import net.minecraft.server.v1_8_R1.EnumEntityUseAction;
import net.minecraft.server.v1_8_R1.Vec3D;

public class PacketEventInUseEntity extends PacketWrapper {
    private int targetId;
    private Object action;
    private Object vector;

    public PacketEventInUseEntity(Object packet, PacketPlayer player) {
        super(packet, player, false);
        targetId = Reflect.Field(packet, int.class, "a").getValue();
        action = Reflect.Field(packet, Object.class, "action").getValue();
        vector = Reflect.Field(packet, Object.class, "c").getValue();
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        Reflect.Field(getPacket(), int.class, "a").setValue(targetId);
        this.targetId = targetId;
    }

    public Object getVector() {
        return vector;
    }

    public void setVector(Vec3D vector) {
        Reflect.Field(getPacket(), Object.class, "c").setValue(vector);
        this.vector = vector;
    }

    public Object getAction() {
        return action;
    }

    public void setAction(EnumEntityUseAction action) {
        Reflect.Field(getPacket(), Object.class, "b").setValue(action);
        this.action = action;
    }
}
