package me.exerosis.packet.wrappers.out;

import me.exerosis.packet.utils.packet.PacketPlay;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;

public class PacketEventOutUpdateTime extends PacketWrapper {

    public PacketEventOutUpdateTime(Object packet) {
        super(packet);
    }

    public PacketEventOutUpdateTime(int time, int age, boolean continueTick) {
        super(PacketPlay.Out.UpdateTime(time, age, continueTick));
    }

    public long getTimeOfDay() {
        return Reflect.Field(getPacket(), long.class, 1).getValue();
    }

    public void setTimeOfDay(long timeOfDay) {
        Reflect.Field(super.getPacket(), long.class, 1).setValue(timeOfDay);
    }

    public long getWorldAge() {
        return Reflect.Field(getPacket(), long.class, 0).getValue();
    }

    public void setWorldAge(long worldAge) {
        Reflect.Field(super.getPacket(), long.class, 0).setValue(worldAge);
    }
}