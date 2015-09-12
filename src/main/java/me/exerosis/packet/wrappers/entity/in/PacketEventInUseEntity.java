package me.exerosis.packet.wrappers.entity.in;

import me.exerosis.packet.utils.packet.EntityUseAction;
import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;
import net.minecraft.server.v1_8_R1.Vec3D;
import org.bukkit.util.Vector;

public class PacketEventInUseEntity extends PacketWrapper {

    public PacketEventInUseEntity(Object packet) {
        super(packet);
    }

    public PacketEventInUseEntity(int entityID, EntityUseAction action, Vector vector) {
        super(PacketPlay.In.UseEntity(entityID, action, vector));
    }

    public int getTargetId() {
        return Reflect.Field(getPacket(), int.class, 0).getValue();
    }

    public void setTargetId(int targetId) {
        Reflect.Field(getPacket(), int.class, 0).setValue(targetId);
    }

    public Object getVector() {
        return Reflect.Field(getPacket(), Object.class, 1).getValue();
    }

    public void setVector(Vec3D vector) {
        Reflect.Field(getPacket(), Object.class, 1).setValue(vector);
    }

    public EntityUseAction getAction() {
        return EntityUseAction.fromNMS(Reflect.Field(getPacket(), Object.class, 0).getValue());
    }

    public void setAction(EntityUseAction action) {
        Reflect.Field(getPacket(), Object.class, 0).setValue(action.toNMS());
    }
}