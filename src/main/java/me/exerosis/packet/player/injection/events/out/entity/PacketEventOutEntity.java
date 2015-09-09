package me.exerosis.packet.player.injection.events.out.entity;

import me.exerosis.packet.player.injection.events.PacketEvent;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.reflection.Reflect;

public class PacketEventOutEntity extends PacketEvent {
    /*
    int id
     */
    public PacketEventOutEntity(Object packet, PacketPlayer player) {
        super(packet, player);
    }

    public double getID() {
        return Reflect.Field(getPacket(), int.class, 0).getValue();
    }

    public void setID(int id) {
        Reflect.Field(getPacket(), int.class, 0).setValue(id);
    }
}