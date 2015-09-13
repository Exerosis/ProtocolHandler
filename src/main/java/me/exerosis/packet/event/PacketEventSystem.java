package me.exerosis.packet.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import me.exerosis.packet.PacketAPI;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.packet.wrappers.in.PacketWrapperInChat;
import me.exerosis.reflection.Reflect;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public final class PacketEventSystem {
    private static ListMultimap<Class<? extends PacketWrapper>, PacketListener> instances = ArrayListMultimap.create();

    private static Map<Class<?>, Class<? extends PacketWrapper>> wrapperLookup = new HashMap<>();

    static {
        addLookup("PacketPlayInChat", PacketWrapperInChat.class);
    }

    private PacketEventSystem() {
    }

    public static <T extends PacketWrapper> void registerListener(Class<T> wrapperClass, PacketListener<T> packetListener) {
        instances.put(wrapperClass, packetListener);
    }

    public static void unregisterListener(PacketListener packetListener) {
        instances.entries().stream().filter(e -> e.getValue().equals(packetListener)).forEach(e -> instances.remove(e.getKey(), e.getValue()));
    }

    @SuppressWarnings("unchecked")
    public static ListenerPseudoInstance fire(PacketWrapper wrapper, PacketPlayer player) {
        Callable<ListenerPseudoInstance> listenerTask = () -> {
            final ListenerPseudoInstance[] instance = {null};
            instances.get(wrapper.getClass()).forEach(l -> instance[0] = ListenerStorage.fire(l, wrapper, player));
            return instance[0];
        };
        try {
            if (wrapper.isSync()) {
                Future<ListenerPseudoInstance> futureTask = Bukkit.getScheduler().callSyncMethod(PacketAPI.getPlugin(), listenerTask);
                try {
                    return futureTask.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
                return listenerTask.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ListenerPseudoInstance fire(Object packet, PacketPlayer player) {
        PacketWrapper wrapper = getWrapper(packet);
        return wrapper == null ? null : fire(wrapper, player);
    }

    public static PacketWrapper getWrapper(Object packet) {
        Class<? extends PacketWrapper> clazz = wrapperLookup.get(Reflect.Class(packet).getClazz());
        if (clazz != null)
            try {
                return (PacketWrapper) Reflect.Class(clazz).getConstructor(0, Object.class).newInstance(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    private static void addLookup(String packetClass, Class<? extends PacketWrapper> packetWrapperClass) {
        wrapperLookup.put(Reflect.Class("{nms}." + packetClass).getClazz(), packetWrapperClass);
    }
}