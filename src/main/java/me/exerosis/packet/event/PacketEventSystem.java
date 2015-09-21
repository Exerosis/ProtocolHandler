package me.exerosis.packet.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import me.exerosis.packet.PacketAPI;
import me.exerosis.packet.player.injection.packet.player.PacketPlayer;
import me.exerosis.packet.wrappers.PacketWrapper;
import me.exerosis.packet.wrappers.entity.out.PacketWrapperOutEntityEffect;
import me.exerosis.packet.wrappers.entity.out.PacketWrapperOutEntityEquipment;
import me.exerosis.packet.wrappers.entity.out.move.PacketWrapperOutEntityHeadRotation;
import me.exerosis.packet.wrappers.entity.out.move.PacketWrapperOutEntityLook;
import me.exerosis.packet.wrappers.entity.out.move.PacketWrapperOutRelEntityMove;
import me.exerosis.packet.wrappers.entity.out.move.PacketWrapperOutRelEntityMoveLook;
import me.exerosis.packet.wrappers.in.*;
import me.exerosis.packet.wrappers.out.PacketWrapperOutResourcePackSend;
import me.exerosis.packet.wrappers.out.PacketWrapperOutSetSlot;
import me.exerosis.packet.wrappers.out.PacketWrapperOutTransaction;
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
        //In
        addLookup("PacketPlayInChat", PacketWrapperInChat.class);
        addLookup("PacketPlayInArmAnimation", PacketWrapperInArmAnimation.class);
        addLookup("PacketPlayInBlockDig", PacketWrapperInBlockDig.class);
        addLookup("PacketPlayInHeldItemSlot", PacketWrapperInHeldItemSlot.class);
        addLookup("PacketPlayInResourcePackStatus", PacketWrapperInResourcePackStatus.class);
        addLookup("PacketPlayInWindowClick", PacketWrapperInWindowClick.class);
        addLookup("PacketPlayInCloseWindow", PacketWrapperInCloseWindow.class);

        //Out
            //Entity
                //Move
                addLookup("PacketPlayOutEntityHeadRotation", PacketWrapperOutEntityHeadRotation.class);
                addLookup("PacketPlayOutEntityLook", PacketWrapperOutEntityLook.class);
                addLookup("PacketPlayOutRelEntityMove", PacketWrapperOutRelEntityMove.class);
                addLookup("PacketPlayOutRelEntityMoveLook", PacketWrapperOutRelEntityMoveLook.class);

            addLookup("PacketPlayOutEntityEffect", PacketWrapperOutEntityEffect.class);
            addLookup("PacketPlayOutEntityEquipment", PacketWrapperOutEntityEquipment.class);

        addLookup("PacketPlayOutResourcePackSend", PacketWrapperOutResourcePackSend.class);
        addLookup("PacketPlayOutSetSlot", PacketWrapperOutSetSlot.class);
        addLookup("PacketPlayOutEntityEffect", PacketWrapperOutEntityEffect.class);
        addLookup("PacketPlayOutTransaction", PacketWrapperOutTransaction.class);
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
    public static PacketEvent fire(PacketWrapper wrapper, PacketPlayer player) {
        Callable<PacketEvent> listenerTask = () -> {
            final PacketEvent[] instance = {null};
            instances.get(wrapper.getClass()).forEach(l -> instance[0] = ListenerStorage.fire(l, wrapper, player));
            return instance[0];
        };

        try {
            if (wrapper.isSync()) {
                Bukkit.getScheduler().runTaskLater(PacketAPI.getPlugin(), new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 0);
                Future<PacketEvent> futureTask = Bukkit.getScheduler().callSyncMethod(PacketAPI.getPlugin(), listenerTask);
                try {
                    return futureTask.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("lol");
            }
            else
                return listenerTask.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PacketEvent fire(Object packet, PacketPlayer player) {
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