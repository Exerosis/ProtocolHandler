package me.exerosis.packet.utils.packet.enums;

import me.exerosis.reflection.Reflect;

public enum EntityUseAction {
    INTERACT,
    ATTACK,
    INTERACT_AT;

    public static EntityUseAction fromNMS(Object object){
        return valueOf(object.toString());
    }

    public Object toNMS() {
        return Reflect.Class("{nms}.EntityUseAction").getMethod("valueOf").call(toString());
    }
}