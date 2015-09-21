package me.exerosis.packet.event;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;

public interface PacketListener<T extends PacketWrapper> {
    void onPacket(PacketEvent<T> listener);
    void onPacket();

    default PacketEvent<T> getEvent() {
        return ListenerStorage.getEvent(this);
    }

    default T getWrapper() {
        return ListenerStorage.getEvent(this).getWrapper();
    }

    default void setWrapper(T wrapper) {
        ListenerStorage.getEvent(this).setWrapper(wrapper);
    }

    default PacketPlayer getPlayer() {
        return ListenerStorage.getEvent(this).getPlayer();
    }

    default void setPlayer(PacketPlayer player) {
        ListenerStorage.getEvent(this).setPlayer(player);
    }

    default boolean isCanceled() {
        return ListenerStorage.getEvent(this).isCanceled();
    }

    default void setCanceled(boolean canceled) {
        ListenerStorage.getEvent(this).setCanceled(canceled);
    }

    default void register(Class<T> wrapperClass) {
        PacketEventSystem.registerListener(wrapperClass, this);
    }

    default void unregister() {
        PacketEventSystem.unregisterListener(this);
    }
}