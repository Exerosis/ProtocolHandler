package me.exerosis.packet.wrappers.in;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;

public class PacketEventInEntityAction extends PacketWrapper {
    private int entityId;
    private int actionId;
    private int jumpBoost;

    public PacketEventInEntityAction(Object packet, PacketPlayer player) {
        super(packet, player);
        entityId = Reflect.Field(packet, int.class, "a").getValue();
        actionId = Reflect.Field(packet, int.class, "b").getValue();
        jumpBoost = Reflect.Field(packet, int.class, "c").getValue();
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
        Reflect.Field(super.getPacket(), int.class, "a").setValue(entityId);
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
        Reflect.Field(super.getPacket(), int.class, "b").setValue(actionId);
    }

    public int getJumpBoost() {
        return jumpBoost;
    }

    public void setJumpBoost(int jumpBoost) {
        this.jumpBoost = jumpBoost;
        Reflect.Field(super.getPacket(), int.class, "c").setValue(jumpBoost);
    }
}
