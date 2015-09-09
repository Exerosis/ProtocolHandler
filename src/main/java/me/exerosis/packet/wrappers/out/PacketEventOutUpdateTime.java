package me.exerosis.packet.wrappers.out;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.reflection.Reflect;

public class PacketEventOutUpdateTime extends PacketWrapper {

    private long worldAge;
    private long timeOfDay;

    public PacketEventOutUpdateTime(Object packet, PacketPlayer player) {
        super(packet, player);
        worldAge = Reflect.Field(packet, long.class, "a").getValue();
        timeOfDay = Reflect.Field(packet, long.class, "b").getValue();
    }

    public long getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(long timeOfDay) {
        this.timeOfDay = timeOfDay;
        Reflect.Field(super.getPacket(), long.class, "b").setValue(timeOfDay);
    }

    public long getWorldAge() {
        return worldAge;
    }

    public void setWorldAge(long worldAge) {
        this.worldAge = worldAge;
        Reflect.Field(super.getPacket(), long.class, "a").setValue(worldAge);
    }
}
