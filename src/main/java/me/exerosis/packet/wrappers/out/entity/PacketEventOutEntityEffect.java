package me.exerosis.packet.wrappers.out.entity;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;

public class PacketEventOutEntityEffect extends PacketEventOutEntity {
    /*
    boolean particles
    byte amplifier
    int duration
    byte effect ID
    */
    public PacketEventOutEntityEffect(Object packet, PacketPlayer player) {
        super(packet, player);
    }

    public boolean areParticlesHidden() {
        return Reflect.Field(getPacket(), byte.class, 2).getValue() == 1;
    }

    public byte getAmplifier() {
        return Reflect.Field(getPacket(), byte.class, 1).getValue();
    }

    public void setAmplifier(byte amplifier) {
        Reflect.Field(getPacket(), byte.class, 1).setValue(amplifier);
    }

    public int getDuration() {
        return Reflect.Field(getPacket(), int.class, 1).getValue();
    }

    public void setDuration(int duration) {
        Reflect.Field(getPacket(), int.class, 1).setValue(duration);
    }

    public byte getEffectID() {
        return Reflect.Field(getPacket(), byte.class, 0).getValue();
    }

    public void setEffectID(byte effectId) {
        Reflect.Field(getPacket(), byte.class, 0).setValue(effectId);
    }

    public void setParticlesHidden(boolean hideParticles) {
        Reflect.Field(getPacket(), byte.class, 2).setValue((byte) (hideParticles ? 1 : 0));
    }
}
