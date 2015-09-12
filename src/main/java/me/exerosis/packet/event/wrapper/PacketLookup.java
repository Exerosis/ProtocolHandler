package me.exerosis.packet.event.wrapper;

import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.packet.wrappers.in.PacketWrapperInChat;
import me.exerosis.reflection.Reflect;
import me.exerosis.reflection.ReflectClass;

import java.util.HashMap;
import java.util.Map;

public final class PacketLookup {
    public static Map<ReflectClass<?>, Class<? extends PacketWrapper>> wrapperLookup = new HashMap<>();

    static {
        wrapperLookup.put(Reflect.Class("{nms}.PacketPlayInChat"), PacketWrapperInChat.class);
    }

    private PacketLookup (){}

    public static PacketWrapper getWrapper(Object packet){
       return Reflect.Class(wrapperLookup.get(Reflect.Class(packet))).newInstance(packet);
    }
}