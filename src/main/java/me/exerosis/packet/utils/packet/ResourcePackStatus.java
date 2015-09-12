package me.exerosis.packet.utils.packet;

import me.exerosis.reflection.Reflect;

public enum ResourcePackStatus {
    SUCCESSFULLY_LOADED,
    DECLINED,
    FAILED_DOWNLOAD,
    ACCEPTED;

    public Object toNMS(){
        return Reflect.Class("{nms}.EnumResourcePackStatus").getMethod("valueOf").call(toString());
    }

    public static ResourcePackStatus fromNMS(Object object){
        return valueOf(object.toString());
    }
}