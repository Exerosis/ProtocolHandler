package me.exerosis.packet.event;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;

public interface PacketListener<T extends PacketWrapper> {
    void onPacket();

    default T getWrapper() {
        return ListenerStorage.getWrapper(this);
    }

    default void setWrapper(T wrapper) {
        ListenerStorage.setWrapper(this, wrapper);
    }

    default PacketPlayer getPlayer() {
        return ListenerStorage.getPlayer(this);
    }

    default void setPlayer(PacketPlayer player) {
        ListenerStorage.setPlayer(this, player);
    }

    default boolean isCanceled() {
        return ListenerStorage.isCanceled(this);
    }

    default void setCanceled(boolean canceled) {
        ListenerStorage.setCanceled(this, canceled);
    }

    default void register(Class<T> wrapperClass) {
        PacketEventSystem.registerListener(wrapperClass, this);
    }

    default void unregister() {
        PacketEventSystem.unregisterListener(this);
    }
}