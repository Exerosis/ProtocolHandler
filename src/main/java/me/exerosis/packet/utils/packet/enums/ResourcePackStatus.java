package me.exerosis.packet.utils.packet.enums;

import me.exerosis.reflection.Reflect;

public enum ResourcePackStatus {
    SUCCESSFULLY_LOADED,
    DECLINED,
    FAILED_DOWNLOAD,
    ACCEPTED;

    public static ResourcePackStatus fromNMS(Object object){
        return valueOf(object.toString());
    }

    public Object toNMS() {
        return Reflect.Class("{nms}.EnumResourcePackStatus").getMethod("valueOf").call(toString());
    }
}