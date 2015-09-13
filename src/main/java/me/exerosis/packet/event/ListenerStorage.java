package me.exerosis.packet.event;

import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;

import java.util.HashMap;
import java.util.Map;

public class ListenerStorage {
    private static Map<PacketListener, ListenerPseudoInstance> instances = new HashMap<>();

    protected static void setCanceled(PacketListener instance, boolean canceled) {
        getInstance(instance).setCanceled(canceled);
    }

    protected static boolean isCanceled(PacketListener instance) {
        return getInstance(instance).isCanceled();
    }

    public static PacketPlayer getPlayer(PacketListener instance) {
        return getInstance(instance).getPlayer();
    }

    public static void setPlayer(PacketListener instance, PacketPlayer player) {
        getInstance(instance).setPlayer(player);
    }

    public static <T extends PacketWrapper> T getWrapper(PacketListener<T> instance) {
        return getInstance(instance).getWrapper();
    }

    public static <T extends PacketWrapper> void setWrapper(PacketListener<T> instance, T wrapper) {
        getInstance(instance).setWrapper(wrapper);
    }

    protected static <T extends PacketWrapper> ListenerPseudoInstance fire(PacketListener<T> instance, T wrapper, PacketPlayer player) {
        ListenerPseudoInstance<T> pseudoInstance = getInstance(instance);

        pseudoInstance.setWrapper(wrapper);
        pseudoInstance.setPlayer(player);
        pseudoInstance.setCanceled(false);

        instance.onPacket();

        return pseudoInstance;
    }

    @SuppressWarnings("unchecked")
    private static <T extends  PacketWrapper> ListenerPseudoInstance<T> getInstance(PacketListener<T> instance) {
        ListenerPseudoInstance<T> pseudoInstance = instances.get(instance);
        if (pseudoInstance != null)
            return pseudoInstance;
        pseudoInstance = new ListenerPseudoInstance<>();
        instances.put(instance, pseudoInstance);
        return pseudoInstance;
    }
}