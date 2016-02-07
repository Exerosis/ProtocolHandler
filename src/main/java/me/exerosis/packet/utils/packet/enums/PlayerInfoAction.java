package me.exerosis.packet.utils.packet.enums;

import me.exerosis.reflection.Reflect;

public enum PlayerInfoAction {
    ADD_PLAYER,
    UPDATE_GAME_MODE,
    UPDATE_LATENCY,
    UPDATE_DISPLAY_NAME,
    REMOVE_PLAYER;

    public static PlayerInfoAction fromNMS(Object object) {
        return valueOf(object.toString());
    }

    public Object toNMS() {
        return Reflect.Class("{nms}.EnumPlayerInfoAction").getMethod("valueOf").call(toString());
    }
}