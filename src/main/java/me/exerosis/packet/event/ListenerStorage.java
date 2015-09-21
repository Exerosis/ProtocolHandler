package me.exerosis.packet.event;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;

import java.util.Map;
import java.util.WeakHashMap;

public class ListenerStorage {
    private static Map<PacketListener, PseudoInstance> instances = new WeakHashMap<>();

    public static <T extends PacketWrapper> PacketEvent<T> getEvent(PacketListener<T> instance) {
        return getInstance(instance).getEvent();
    }

    public static <T extends PacketWrapper> void setEvent(PacketListener<T> instance, PacketEvent<T> event) {
        getInstance(instance).setEvent(event);
    }

    protected static <T extends PacketWrapper> PacketEvent fire(PacketListener<T> instance, T wrapper, PacketPlayer player) {
        PseudoInstance<T> pseudoInstance = getInstance(instance);

        PacketEvent<T> event = new PacketEvent<>(wrapper, player);
        pseudoInstance.setEvent(event);
        instance.onPacket(event);
        instance.onPacket();
        return event;
    }

    @SuppressWarnings("unchecked")
    private static <T extends  PacketWrapper> PseudoInstance<T> getInstance(PacketListener<T> instance) {
        PseudoInstance<T> pseudoInstance = instances.get(instance);
        if (pseudoInstance != null)
            return pseudoInstance;
        pseudoInstance = new PseudoInstance<>();
        instances.put(instance, pseudoInstance);
        return pseudoInstance;
    }

    public static class PseudoInstance<T extends PacketWrapper> {
        private PacketEvent<T> event;

        public void setEvent(PacketEvent<T> event) {
            this.event = event;
        }

        public PacketEvent<T> getEvent() {
            return event;
        }
    }
}