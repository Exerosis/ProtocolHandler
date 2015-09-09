package me.exerosis.packet.player.injection.events.out;

import me.exerosis.packet.player.injection.events.PacketEvent;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;

public class PacketEventOutEntityEffect extends PacketEvent {
    private int entityId;
    private byte effectId;
    private byte amplifier;
    private int duration;
    private boolean hideParticles;

    public PacketEventOutEntityEffect(Object packet, PacketPlayer player) {
        super(packet, player);
        entityId = Reflect.Field("a", packet, int.class).getValue();
        effectId = Reflect.Field("b", packet, byte.class).getValue();
        amplifier = Reflect.Field("c", packet, byte.class).getValue();
        duration = Reflect.Field("d", packet, int.class).getValue();
        byte e = Reflect.Field("e", packet, byte.class).getValue();
        if (e == 1)
            hideParticles = true;
        hideParticles = false;
    }

    public boolean areParticlesHidden() {
        return hideParticles;
    }

    public byte getAmplifier() {
        return amplifier;
    }

    public void setAmplifier(byte amplifier) {
        Reflect.Field("c", super.getPacket(), byte.class).setValue(amplifier);
        this.amplifier = amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        Reflect.Field("d", super.getPacket(), int.class).setValue(duration);
        this.duration = duration;
    }

    public byte getEffectId() {
        return effectId;
    }

    public void setEffectId(byte effectId) {
        Reflect.Field("b", super.getPacket(), byte.class).setValue(effectId);
        this.effectId = effectId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        Reflect.Field("a", super.getPacket(), int.class).setValue(entityId);
        this.entityId = entityId;
    }

    public void particlesAreHidden(boolean hideParticles) {
        this.hideParticles = hideParticles;
        //TODO fix this please!
    }
}
