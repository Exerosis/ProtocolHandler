package me.exerosis.packet.utils.packet;

import me.exerosis.reflection.Reflect;

public enum EntityUseAction {
    INTERACT,
    ATTACK,
    INTERACT_AT;

    public Object toNMS() {
        return Reflect.Class("{nms}.EnumResourcePackStatus").getMethod("valueOf").call(toString());
    }

    public static EntityUseAction fromNMS(Object object){
        return valueOf(object.toString());
    }
}