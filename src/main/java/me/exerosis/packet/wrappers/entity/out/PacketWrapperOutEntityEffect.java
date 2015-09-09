package me.exerosis.packet.wrappers.entity.out;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.wrappers.entity.PacketWrapperEntity;
import me.exerosis.reflection.Reflect;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

//TODO undone
public class PacketWrapperOutEntityEffect extends PacketWrapperEntity {
    /*
    boolean particles
    byte amplifier
    int duration
    byte effect ID
    */
    public PacketWrapperOutEntityEffect(int entityID, int effectID, int duration, int amplifier, boolean ambient) {
        super(PacketPlay.Out.EntityEffect(entityID, effectID, duration, amplifier, ambient));
    }

    public PacketWrapperOutEntityEffect(int entityID, Object mobEffect) {
        super(PacketPlay.Out.EntityEffect(entityID, mobEffect));
    }

    public PacketWrapperOutEntityEffect(int entityID, PotionEffect effect) {
        super(PacketPlay.Out.EntityEffect(entityID, effect));
    }

    public PacketWrapperOutEntityEffect(int entityID, PotionEffectType type, int duration, int amplifier, boolean ambient) {
        super(PacketPlay.Out.EntityEffect(entityID, type, duration, amplifier, ambient));
    }

    public PacketWrapperOutEntityEffect(Object packet) {
        super(packet);
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